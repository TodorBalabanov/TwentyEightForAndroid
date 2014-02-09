/*******************************************************************************
 *                                                                             *
 * Twenty-Eight for Android is port of popular Asian card game called Rosanne: *
 * Twenty-eight (28) <http://sourceforge.net/projects/rosanne/>. Project       *
 * development is done as NBU Java training course held in Sofia, Bulgaria.    *
 *                                                                             *
 * Copyright (C) 2013-2014 by Todor Balabanov  ( tdb@tbsoft.eu )               *
 *                                                                             *
 * This program is free software: you can redistribute it and/or modify        *
 * it under the terms of the GNU General Public License as published by        *
 * the Free Software Foundation, either version 3 of the License, or           *
 * (at your option) any later version.                                         *
 *                                                                             *
 * This program is distributed in the hope that it will be useful,             *
 * but WITHOUT ANY WARRANTY; without even the implied warranty of              *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the               *
 * GNU General Public License for more details.                                *
 *                                                                             *
 * You should have received a copy of the GNU General Public License           *
 * along with this program. If not, see <http://www.gnu.org/licenses/>.        *
 *                                                                             *
 ******************************************************************************/

package eu.veldsoft.twenty.eight.ai;

//#ifndef _AI_SUITLENGTHSOLVER_H_
//#define _AI_SUITLENGTHSOLVER_H_
////#include <assert.h>
////#include <memory.h>
////#include <stdio.h>
//#include "wx/wx.h"

// Super type for holding suit length for hands

typedef int  slMatrix[slTOTAL_HANDS][slTOTAL_SUITS];

// The data which represents played cards, which is provided as one of the inputs to the
// suit length solver. The format is identical to that of slProblem

typedef slMatrix slPlayed;

// Solution to the problem

typedef slMatrix slSolution;

// The data which represents the problem which is provided as one of the inputs to the
// suit length solver.

class slPROBLEM
{
	slMatrix suit_length;
	int suit_total_length[slTOTAL_SUITS];
	int hand_total_length[slTOTAL_HANDS];
}slProblem;

// Working data for computing the solution.

// Each cell will have
// a) min - the minimum number of cards which must be allocated
// b) max - the maximum number ofa cards that can be callocated
// c) suit_length - number of cards allocated. If it is vacant, the value is slVACANT

class slCELL
{
	int min;
	int max;
	int suit_length;
}slCell;
class slDATA
{
	slCell cells[slTOTAL_HANDS][slTOTAL_SUITS];

	int suit_total_length[slTOTAL_SUITS];
	int hand_total_length[slTOTAL_HANDS];
	int suit_allocated[slTOTAL_SUITS];
	int hand_allocated[slTOTAL_HANDS];

	int suit_sum_of_maxs[slTOTAL_SUITS];
	int hand_sum_of_maxs[slTOTAL_HANDS];

    // This is not really the sum of min values.
    // It is the sum of min values of vacant cells
	int suit_sum_of_vacant_mins[slTOTAL_SUITS];
	int hand_sum_of_vacant_mins[slTOTAL_HANDS];

}slData;

class aiSuitLengthSolver
{
private:
	slProblem m_problem;
	slData m_saved, m_working;
	slPlayed m_played;
	int m_suit_sum_of_min[slTOTAL_SUITS];
	int m_hand_sum_of_min[slTOTAL_HANDS];
	// Disallow copy constructor/assignment operators
	aiSuitLengthSolver(final aiSuitLengthSolver &);
    aiSuitLengthSolver & operator=(final aiSuitLengthSolver &);
    void InitializeWorkingData(slData *data);
    boolean RecalcCellMax(slData *data, int hand, int suit);
	boolean SetCell(slData *data, int hand, int suit, int suit_length);
	boolean RecalcMaxForImpactedCells(slData *data, int hand, int suit);
	boolean RecalcMinForImpactedCells(slData *data, int hand, int suit);
	//boolean RecalcSumOfMaxForAllCells()
	boolean RecalcCellMin(slData *data, int hand, int suit);
	boolean RecalcMinForAllCells(slData *data, boolean * changed = null);
	boolean RecalcMaxForAllCells(slData *data);
	int GenerateRandomFill(int min, int max);

public:
	aiSuitLengthSolver();
	~aiSuitLengthSolver();
	static void InitializeProblem(slProblem *problem);
	static void InitializePlayed(slPlayed played);
	boolean SetProblem(slProblem *problem, slPlayed played);
	boolean GenerateRandomSolution(slSolution solution);
	//static String PrintProblem(slProblem *problem);
	static String PrintData(slData *data);
	static String PrintMatrix(slMatrix matrix);
	String PrintSavedData();
	String PrintWorkingData();
	//static String PrintSolution(slSolution *solution);
};



//#endif


//




//




//




// What is the suit length problem?
// ********************************
//
// In the following matrix where we have 4 hands (h(1) to h(4)) holding cards from 4 suits (s(1) to s(4)),
// c(x,y) represents the number of cards beloning to suit (y) held by hand (x)
//
//     |  s(1)|  s(2)|  s(3)|  s(4)
// ----+------+------+------+------
// h(1)|c(1,1)|c(1,2)|c(1,3)|c(1,4)
// ----+------+------+------+------
// h(2)|c(2,1)|c(2,2)|c(2,3)|c(2,4)
// ----+------+------+------+------
// h(3)|c(3,1)|c(3,2)|c(3,3)|c(3,4)
// ----+------+------+------+------
// h(4)|c(4,1)|c(4,2)|c(4,3)|c(4,4)
//
//
// Given one or more c(x,y) are unknown, the object of the problem is to fill these randomly.
//
// Preconditions
// *************
//
// Let sum(h(x)) be (c(n,1) + c(n,2) + c(n,3) + c(n,4)), the sum of cards in hand h(x).
// Similary let sum(s(y)) be the sum of cards in suit s(y).
// All sum(h(x)) and (s(y)) are known.
//
// Solution
// ********
//
// The c(x,y) which are unknown are called vacant cells.
//
// Let filled(h(x)) be the sum of cards already filled in previously vacant cells in hand h(x). This will be zero before
// solving the problem and will increase as the problem gets solved, as solving the problem involves filling vacant cells.
// Similarly, let filled(s(y)) be the usm of cards already filled in previously vacant cells for suit s(y).
//
// Each vacant cell c(x,y) will have a max(c(x,y)) and min(c(x,y)).
// max(c(x,y)) is the maximum number of cards which can be allocated to the vacant cell c(x,y).
// Similarly, min(c(x,y)) is the minimum number of cards which should be allocated to the vacant cell c(x,y).
//
// Vacant cells, once filled will have min(c(x,y)) = max(c(x,y)) = (suit length for the cell)
//
//
// Let sum_min(h(x)) be the sum of min values for all cells in hand h(x) which have not been filled.
// let sum_min(s(y)) be the sum of min values for all cells in suit s(y) which have not been filled.
//
// Let MIN be a function such that, MIN(x,y) returns minimum value amongst x and y.
// Let MAX be a function such that, MAX(x,y) returns maximum value amongst x and y.
//
// max(c(x,y)) = MIN(
//                   sum(h(x)) - filled(h(x)) - sum_min(h(x)) + min(c(x,y)),
//                   sum(s(y)) - filled(s(y)) - sum_min(s(y)) + min(c(x,y))
//                  )
//
// Let sum_max(h(x)) be (max(c(x,1)) + max(c(x,2)) + max(c(x,3)) + max(c(x,4)))
// and sum_max(s(y)) be (max(c(1,y)) + max(c(2,y)) + max(c(3,y)) + max(c(4,y)))
//
// There are totally sum(h(x)) cards to be distributed among c(x, n), n=1 to 4.
// For cells c(x,n) where n !=y, the maximum total which can be distributed is sum_max(h(x)) + max(c(x,y)).
// So, c(x,y) should be allocated at least sum(h(x)) - sum_max(h(x)) + max(c(x,y)).
//
// min(c(x,y)) = MAX(
//                  (sum(h(x)) - sum_max(h(x)) + max(c(x,y))),
//                  (sum(s(y)) - sum_max(s(y)) + max(c(x,y)))
//                  )
//
// The following algorithm is then used,
//
// for each vacant cell c(x,y):
//     Get a random(non-linear) i such that min(c(x,y)) <= i <=max(c(x,y))  ---. Refer to the section below
//     Set c(x,y) = i = max(c(x,y)) = min(c(x,y))
//     Recalculate sum_max for h(x) and s(y).
//     Recalculate sum_min for h(x) and s(y).
//     Recalcualte filled for h(x) and s(y).
//     Recalculate max for all impacted cells. That is, all cells in h(x) and s(y).
//     Recalculate min for all impacted cells. That is, all cells in h(x) and s(y).
//
// The last two steps are recursive as change of max for a cell will impact min of other cells and vice versa.
//
// Computation of relative probability
// ***********************************
//
// The process of shuffling generates various permutations of cards.
//
// While solving the suit length problem, a slot where at the most m cards can be accommodated needs
// to be filled randomly with n cards where 0 <= n <= m. Now, the probability of this slot getting filled
// with a zero or m is quite less compared to mod(m/2), the latter being the most probable.
// Hence we cannot pick a number n using a pseudo random number generator satisfying 0 <= n <= m ,
// as the probability for all n is not equal.
//
// The probability of a slot being filled with n, given
// (t) is the total number of cards in the deck used for play,
// (s) the size of the slot open to be filled up, and
// (a) the available number of cards in the suit is given by
//
// (sCn * (t-a)P(s-n) * aPn) / (tPs)
//
// Total number of ways in which (s) cards can be dealt from a total of (t) cards is (tPs).
// The possible ways in which (s-n) cards can be dealt from (t-a) and (n) cards from (a) is (sCn * (t-a)P(s-n) * aPn).
// (sCn) provides the number of ways in which the two separate permutations of
// ((t-a)P(s-n)) and (aPn) can be combined together.
//
// And finally to complicate matters further, we want the generated solution to mirror real-world shuffling.
// Hence the process of filling vacant slots should consider played cards also while computing relative probability.
//
// The algorithm used is as following:
//
// Let there be a slot c(x,y) which is vacant.
// Let a = min(c(x,y)) and b = max(c(x,y)).
// Let played(x,y) be the cards of suit y belonging to hand x which have already been played.
// We ned to pick random i such that a <= i <=b
//
// For i in range a to b
//     Check the probability p(i) of hand x getting (i + played(x,y)) cards of suit y
//
// Please note that in the formula (sCn * (t-a)P(s-n) * aPn) / (tPs), t and s are constant and the denominator can be
// ignore for this calculation. So p(i) is only the numberator
//
// Let sum_played(x,y) be sum of p(i) when i=a to b
// Pick a random number j between 0 and sum_played(x,y)
//
// if j is between 0 and p(0) pick i as 0
// if j is between p(0) and p(1) pick i as 1
// Generalizing
// if j is between p(k-1) and p(k) i = k.


//#include "ai/aisuitlengthsolver.h"
//#include "SFMT.h"
//#include "SFMT-params.h"

////#define slLOG_DEBUG_SETPROBLEM 0
////#define slLOG_DEBUG_SETCELL 0
////#define slLOG_DEBUG_RECALCCELL_MAX 0
////#define slLOG_DEBUG_RECALCCELL_MIN 0
////#define slLOG_DEBUG_GETRANDSOLN 0
////#define slLOG_DEBUG_SETIMPCELLS 0
////#define slLOG_DEBUG_GENRANDFILL 0

// The probability of a hand of 8 cards to get 0 to 8 cards of the same suit
static final int SL_SUIT_LEN_PROBS[] = {699230, 2632395, 3582982, 2262936, 707167, 107759, 7347, 183, 1};
//static final int SL_SUIT_LEN_TOTAL_PROB = 10000000;


aiSuitLengthSolver.aiSuitLengthSolver()
{
	return;
}
aiSuitLengthSolver.~aiSuitLengthSolver()
{
	return;
}

void aiSuitLengthSolver.InitializeProblem(slProblem *problem)
{
    int i = 0;
    int j = 0;

    // Problem is initialized by setting suit lengths for all spots as vacant
    // and setting the total length for all hands and suits as zero.

    memset(problem, 0, sizeof(slProblem));
    for(i = 0; i < slTOTAL_HANDS; i++)
    {
        for(j = 0; j < slTOTAL_SUITS; j++)
        {
            problem.suit_length[i][j] = slVACANT;
        }
    }

    return;
}

void aiSuitLengthSolver.InitializePlayed(slPlayed played)
{
    // Played is initialized by setting suit lengths for all spots as zero
    // and setting the total length for all hands and suits also as zero.

    memset(played, 0, sizeof(slPlayed));

    return;
}

void aiSuitLengthSolver.InitializeWorkingData(slData *data)
{
    int i = 0;
    int j = 0;

    // Problem is initialized by setting suit lengths for all spots as vacant
    // and setting the total length for all hands and suits as zero.

    memset(data, 0, sizeof(slData));
    for(i = 0; i < slTOTAL_HANDS; i++)
    {
        for(j = 0; j < slTOTAL_SUITS; j++)
        {
            data.cells[i][j].suit_length = slVACANT;
        }
    }

    return;
}


boolean aiSuitLengthSolver.SetProblem(slProblem *problem, slPlayed played)
{
    int i = 0;
    int j = 0;
    int hand_total_played[slTOTAL_HANDS] = {0, 0, 0, 0};
    int suit_total_played[slTOTAL_SUITS] = {0, 0, 0, 0};

    if(problem == null)
    {
        .wxLogError(String.Format(("Input variable problem for aiSuitLengthSolver.SetProblem is null. %s:%d"),
                                    (__FILE__), __LINE__));
        return false;
    }
    if(played == null)
    {
        .wxLogError(String.Format(("Input variable played for aiSuitLengthSolver.SetProblem is null. %s:%d"),
                                    (__FILE__), __LINE__));
        return false;
    }

	InitializeWorkingData(&m_saved);

	// Create a copy of the problem

	memcpy(&m_problem, problem, sizeof(slProblem));
	memcpy(m_played, played, sizeof(slPlayed));

	// Check the sanity of data.
	// The array played contains the list of played cards.
	// The hand_total_length and suit_total_length arrays in problem contain the list of cards to be allocatd.
	// Both added together for should be 8

    for(i = 0; i < slTOTAL_HANDS; i++)
    {
        for(j = 0; j < slTOTAL_SUITS; j++)
        {
            hand_total_played[i] += m_played[i][j];
            suit_total_played[j] += m_played[i][j];
        }
    }

    //wxLogDebug(PrintMatrix(m_played));
    for(i = 0; i < slTOTAL_HANDS; i++)
    {
        if((hand_total_played[i] + m_problem.hand_total_length[i]) != slLENGTH_MAX)
        {
            .wxLogError(String.Format(("No of played cards and the cards to be set does not add up to slLENGTH_MAX for hand %d. %s:%d"),
                                        i, (__FILE__), __LINE__));
            .wxLogError(String.Format(("hand_total_played[%d] = %d"), i , hand_total_played[i]));
            .wxLogError(String.Format(("m_problem.hand_total_length[%d] = %d"), i , m_problem.hand_total_length[i]));
            assert(false);
            return false;
        }
    }

    for(i = 0; i < slTOTAL_SUITS; i++)
    {
        if((suit_total_played[i] + m_problem.suit_total_length[i]) != slLENGTH_MAX)
        {
            .wxLogError(String.Format(("No of played cards and the cards to be set does not add up to slLENGTH_MAX for suit %d. %s:%d"),
                                        i, (__FILE__), __LINE__));
            .wxLogError(String.Format(("suit_total_played[%d] = %d"), i , suit_total_played[i]));
            .wxLogError(String.Format(("m_problem.suit_total_length[%d] = %d"), i , m_problem.suit_total_length[i]));
            assert(false);
            return false;
        }
    }

	// Copy the status of each cell (which must be zero to slLENGTH_MAX or slVACANT)

	for(i = 0; i < slTOTAL_HANDS; i++)
	{
	    for(j = 0; j < slTOTAL_SUITS; j++)
	    {
	        assert((m_problem.suit_length[i][j] == slVACANT) ||
                  ((m_problem.suit_length[i][j] >= 0) && (m_problem.suit_length[i][j] <= slLENGTH_MAX)));

            //SetCell(&m_saved, i, j, m_problem.suit_length[i][j]);
	        m_saved.cells[i][j].suit_length = m_problem.suit_length[i][j];

	        // If the suit length for the cell is fixed already
	        // set max and min as the fixed value.

	        if (m_problem.suit_length[i][j] != slVACANT)
	        {
	            m_saved.cells[i][j].max = m_problem.suit_length[i][j];
	            m_saved.cells[i][j].min = m_problem.suit_length[i][j];

	            // Adjust the sum of maxes
	            m_saved.hand_sum_of_maxs[i] += m_saved.cells[i][j].max;
	            m_saved.suit_sum_of_maxs[j] += m_saved.cells[i][j].max;

	            // Adjust allocated cards
	            m_saved.hand_allocated[i] += m_problem.suit_length[i][j];
	            m_saved.suit_allocated[j] += m_problem.suit_length[i][j];
	        }

	    }
	}

	// Calc max for each cell
	// Zero or -1 will not affect alloc
	// Calculate sum of maxes
	// Calculate min

    memcpy(&(m_saved.hand_total_length), &(m_problem.hand_total_length), sizeof(m_saved.hand_total_length));
    memcpy(&(m_saved.suit_total_length), &(m_problem.suit_total_length), sizeof(m_saved.suit_total_length));

    // Calculate max for all cells
    // This also calculates the sum of maxes for all hands and suits internally

    RecalcMaxForAllCells(&m_saved);

    // Calculate min for all cells
    // TODO: Is this required?

    //RecalcMinForAllCells(&m_saved);

	for(i = 0; i < slTOTAL_HANDS; i++)
	{
	    for(j = 0; j < slTOTAL_SUITS; j++)
	    {
	        RecalcMinForImpactedCells(&m_saved, i, j);
	    }

    }

    // Calcualte max for all cells. This is done again because mins would have changed.

    //RecalcMaxForAllCells(&m_saved);
    //RecalcMinForAllCells(&m_saved);
    return true;
}

boolean aiSuitLengthSolver.RecalcCellMax(slData *data, int hand, int suit)
{
    int old_max = 0;
    int new_max = 0;

    assert(data != null);
    assert(hand < slTOTAL_HANDS);
    assert(suit < slTOTAL_SUITS);
    assert(data.cells[hand][suit].suit_length == slVACANT);

    int h = (data.hand_total_length[hand] - data.hand_allocated[hand] - data.hand_sum_of_vacant_mins[hand]
             + data.cells[hand][suit].min);
    assert(h >= 0);
    int s = (data.suit_total_length[suit] - data.suit_allocated[suit] - data.suit_sum_of_vacant_mins[suit]
             + data.cells[hand][suit].min);
    assert(s >= 0);

    // Store the old max value

    old_max = data.cells[hand][suit].max;

    // Compute the new max value

    new_max = std.min(h, s);
    assert((new_max <= old_max) || (old_max == 0));

    data.cells[hand][suit].max = new_max;

    // If the new max value is different from the older one,
    // then the sum of max values for the hand and the suit has to be corrected

    if(new_max != old_max)
    {
        data.hand_sum_of_maxs[hand] -= (old_max - new_max);
        data.suit_sum_of_maxs[suit] -= (old_max - new_max);
        return true;
    }

    return false;
}

boolean aiSuitLengthSolver.SetCell(slData *data, int hand, int suit, int val)
{

//#ifdef slLOG_DEBUG_SETCELL
    wxLogDebug(String.Format(("Entering aiSuitLengthSolver.SetCell (%d, %d) = %d"), hand, suit, val));
//#endif

    assert(data != null);
    assert(hand < slTOTAL_HANDS);
    assert(suit < slTOTAL_SUITS);

    assert((val >= 0) || (val <= slLENGTH_MAX));

    // SetCell should be recalculated only for vacant slots

    assert(data.cells[hand][suit].suit_length == slVACANT);

    // Fix the suit length for the cell

    data.cells[hand][suit].suit_length = val;

    // As the max value for the cell is changing, correct the sum of maxes beforehand

    data.hand_sum_of_maxs[hand] -= (data.cells[hand][suit].max - val);
    data.suit_sum_of_maxs[suit] -= (data.cells[hand][suit].max - val);

    // As the min value for the cell is changing, correct the sum of mins beforehand

    if(data.cells[hand][suit].min > 0)
    {
        data.hand_sum_of_vacant_mins[hand] -= data.cells[hand][suit].min;
        data.suit_sum_of_vacant_mins[suit] -= data.cells[hand][suit].min;
    }

    // Fix the max and min as the same as the suit length

    data.cells[hand][suit].max = val;
    data.cells[hand][suit].min = val;

    // val number of cards have been allocated from the hand and from the suit

    data.hand_allocated[hand] += val;
    data.suit_allocated[suit] += val;
    assert(data.hand_allocated[hand] <= data.hand_total_length[hand]);
    assert(data.suit_allocated[suit] <= data.suit_total_length[suit]);

    // Recalculate the max for all the affected cells

    RecalcMaxForImpactedCells(data, hand, suit);
    RecalcMinForImpactedCells(data, hand, suit);

    // Recalculate the min for all the cells

    //RecalcMinForAllCells(data);

    // Calcualte max for all cells. This is done again because mins would have changed.

    //RecalcMaxForAllCells(data);
    //RecalcMinForAllCells(data);

    return true;
}

boolean aiSuitLengthSolver.RecalcMaxForImpactedCells(slData *data, int hand, int suit)
{
    int i = 0;

    assert(data != null);
    assert(hand < slTOTAL_HANDS);
    assert(suit < slTOTAL_SUITS);

//#ifdef slLOG_DEBUG_SETIMPCELLS

    .wxLogDebug(String.Format(("Entering aiSuitLengthSolver.RecalcMaxForImpactedCells for (%d, %d)"), hand, suit));
    .wxLogDebug(String.Format(("Data : %s"), PrintData(data).c_str()));

//#endif

    // Recalculate the max for all the affected cells
    // Recalculate the max for all cell in hand


    for(i = 0; i < slTOTAL_SUITS; i++)
    {
        // Avoid recalculating for the cell for which data is being set
        // and for non-vacant cells.

        //if((i != suit) && (data.cells[hand][i].suit_length == slVACANT))
        if(data.cells[hand][i].suit_length == slVACANT)
        {
            if(RecalcCellMax(data, hand, i) == true)
            {
                RecalcMinForImpactedCells(data, hand, i);
            }
        }
    }
    // Recalculate the max for all cell in suit

    for(i = 0; i < slTOTAL_HANDS; i++)
    {
        // Avoid recalculating for the cell for which data is being set
        // and for non-vacant cells.

        //if((i != hand) && (data.cells[i][suit].suit_length == slVACANT))
        if(data.cells[i][suit].suit_length == slVACANT)
        {
            if(RecalcCellMax(data, i, suit) == true)
            {
                RecalcMinForImpactedCells(data, i, suit);
            }
        }
    }
    return true;
}

boolean aiSuitLengthSolver.RecalcMinForImpactedCells(slData *data, int hand, int suit)
{
    int i = 0;

    assert(data != null);
    assert(hand < slTOTAL_HANDS);
    assert(suit < slTOTAL_SUITS);

//#ifdef slLOG_DEBUG_SETIMPCELLS

    .wxLogDebug(String.Format(("Entering aiSuitLengthSolver.RecalcMinForImpactedCells for (%d, %d)"), hand, suit));
    .wxLogDebug(String.Format(("Data : %s"), PrintData(data).c_str()));

//#endif

    // Recalculate the min for all the affected cells
    // Recalculate the min for all cell in hand

    for(i = 0; i < slTOTAL_SUITS; i++)
    {
        // Avoid recalculating for the cell for which data is being set
        // and for non-vacant cells.

        //if((i != suit) && (data.cells[hand][i].suit_length == slVACANT))
        if(data.cells[hand][i].suit_length == slVACANT)
        {
            if(RecalcCellMin(data, hand, i) == true)
            {
                RecalcMaxForImpactedCells(data, hand, i);
            }
        }
    }
    // Recalculate the max for all cell in suit

    for(i = 0; i < slTOTAL_HANDS; i++)
    {
        // Avoid recalculating for the cell for which data is being set
        // and for non-vacant cells.

        //if((i != hand) && (data.cells[i][suit].suit_length == slVACANT))
        if(data.cells[i][suit].suit_length == slVACANT)
        {
            if(RecalcCellMin(data, i, suit) == true)
            {
                RecalcMaxForImpactedCells(data, i, suit);
            }
        }
    }
    return true;
}
boolean aiSuitLengthSolver.RecalcCellMin(slData *data, int hand, int suit)
{
    int i = 0;
    int j = 0;
    int old_min = 0;
    int new_min = 0;

    assert(data != null);
    assert(hand < slTOTAL_HANDS);
    assert(suit < slTOTAL_SUITS);
    assert(data.cells[hand][suit].suit_length == slVACANT);

//  min(c(x,y)) = MAX(
//                       (sum(h(x)) - sum_max(h(x)) + max(c(x,y))),
//                       (sum(s(y)) - sum_max(s(y)) + max(c(x,y)))

//                   )
//
//  If min(c(x,y)) is negative, set this as zero.

    i = data.hand_total_length[hand] - data.hand_sum_of_maxs[hand] + data.cells[hand][suit].max;
    if(i < 0)
    {
        i = 0;
    }

    j = data.suit_total_length[suit] - data.suit_sum_of_maxs[suit] + data.cells[hand][suit].max;
    if(j < 0)
    {
        j = 0;
    }

    old_min = data.cells[hand][suit].min;
    new_min = std.max(i, j);

    // The new minimum should be equal to or more than the old minimum

    assert(new_min >= old_min);

    data.cells[hand][suit].min = new_min;

    // If the new min is different from the old min, correct the sum of vacant mins

    if(new_min != old_min)
    {
        data.hand_sum_of_vacant_mins[hand] += (new_min - old_min);
        data.suit_sum_of_vacant_mins[suit] += (new_min - old_min);
        return true;
    }

    return false;
}
boolean aiSuitLengthSolver.RecalcMinForAllCells(slData *data, boolean * changed)
{
    int i = 0;
    int j = 0;

    assert(data != null);

    // Reset the sum of min values for all hands and suits
    //memset(data.hand_sum_of_vacant_mins, 0, sizeof(data.hand_sum_of_vacant_mins));
    //memset(data.suit_sum_of_vacant_mins, 0, sizeof(data.suit_sum_of_vacant_mins));

    // Calculate min for all cells
    // At the same time, calculate the sum of mins for the hand

	for(i = 0; i < slTOTAL_HANDS; i++)
	{
	    for(j = 0; j < slTOTAL_SUITS; j++)
	    {
	        // If the suit length for a cell is fixed, then min
	        // has already been calculated.

	        if(data.cells[i][j].suit_length == slVACANT)
	        {
                RecalcCellMin(data, i, j);
                assert((data.cells[i][j].min >= 0) && (data.cells[i][j].min <= slLENGTH_MAX));

                // If the min for a vacant cell is not zero, add that to the sum of mins for vacant cells

                //if(data.cells[i][j].min > 0)
                //{
                //    data.hand_sum_of_vacant_mins[i] += data.cells[i][j].min;
                //    data.suit_sum_of_vacant_mins[j] += data.cells[i][j].min;
                //}
	        }

	    }
	}
	return true;
}

boolean aiSuitLengthSolver.RecalcMaxForAllCells(slData *data)
{
    int i = 0;
    int j = 0;

    assert(data != null);

    // Calculate max for each cell
    // This also calculates the sum of maxes for all hands and suits internally

	for(i = 0; i < slTOTAL_HANDS; i++)
	{
	    for(j = 0; j < slTOTAL_SUITS; j++)
	    {
	        // If the suit length for a cell is fixed, then max
	        // has already been calculated.

	        if(data.cells[i][j].suit_length == slVACANT)
	        {
                RecalcCellMax(data, i, j);

	        }
	    }
	}

    return true;
}

// This method picks a value between min and max, both inclusive, to fill a vacant slot.
// However, the probability of a slot to be filled with min is different from probabilityof a slot to be filled with max.
// Hence the relative probability is computed and stored in SL_SUIT_LEN_PROBS and this is used

int aiSuitLengthSolver.GenerateRandomFill(int min, int max)
{
    assert(min >= 0);
    assert(min < max);
    assert(max <= slLENGTH_MAX);

    int i = 0;
    int total = 0;
    int cumul[] = {0, 0, 0, 0, 0, 0, 0, 0, 0};
    int rand_val = 0;

    for(i = min; i <= max; i++)
    {
        total += SL_SUIT_LEN_PROBS[i];
        cumul[i] = total;
    }


    rand_val = (gen_rand32() % (total + 1));

//#ifdef slLOG_DEBUG_GENRANDFILL
    wxLogDebug(String.Format(("min = %d"), min));
    wxLogDebug(String.Format(("max = %d"), max));
    for(i = min; i <= max; i++)
    {
        wxLogDebug(String.Format(("cumul[%d] = %u"), i, cumul[i]));
    }
    wxLogDebug(String.Format(("total = %u"), total));
    wxLogDebug(String.Format(("rand_val = %u"), rand_val));
//#endif
    for(i = min; i <= max; i++)
    {
        if(rand_val <= cumul[i])
        {
//#ifdef slLOG_DEBUG_GENRANDFILL
            wxLogDebug(String.Format(("GenerateRandomFill returns %d"), i));
//#endif
            return i;
        }
    }
    assert_MSG(false, ("Control should not reach here"));
    return -1;

}

boolean aiSuitLengthSolver.GenerateRandomSolution(slSolution solution)
{
	int i = 0;
	int j = 0;
    int fill = 0;

    if(solution == null)
    {
        return false;
    }

	memcpy(&m_working, &m_saved, sizeof(slData));
//#ifdef slLOG_DEBUG_GETRANDSOLN
    wxLogDebug(("Problem :"));
    wxLogDebug(PrintData(&m_working));
//#endif

	for(i = 0; i < slTOTAL_HANDS; i++)
	{
	    for(j = 0; j < slTOTAL_SUITS; j++)
	    {
	        // Fill only if the cell is vacant

            if(m_working.cells[i][j].suit_length == slVACANT)
            {
                // In a vacant cell, if max and min are the same, then that is the only option.
                // No need to try to get a random number.

                if(m_working.cells[i][j].max == m_working.cells[i][j].min)
                {
                    fill = m_working.cells[i][j].max;
                }
                else
                {
                    //fill = m_working.cells[i][j].min + (gen_rand32() % (m_working.cells[i][j].max - m_working.cells[i][j].min + 1));
                    fill = GenerateRandomFill(m_working.cells[i][j].min, m_working.cells[i][j].max);
                }
//#ifdef slLOG_DEBUG_GETRANDSOLN
                .wxLogDebug(String.Format(("Attempting to fill (%d, %d) with %d"), i, j, fill));
//#endif
                SetCell(&m_working, i, j, fill);
//#ifdef slLOG_DEBUG_GETRANDSOLN
                .wxLogDebug(PrintData(&m_working));
//#endif
            }
	    }
	}

    // Set the solution

	for(i = 0; i < slTOTAL_HANDS; i++)
	{
	    for(j = 0; j < slTOTAL_SUITS; j++)
	    {
            solution[i][j] = m_working.cells[i][j].suit_length;
	    }
	}

//#ifdef slLOG_DEBUG_GETRANDSOLN
    .wxLogDebug(PrintMatrix(solution));
//#endif
    return true;
}

String aiSuitLengthSolver.PrintSavedData()
{
    return aiSuitLengthSolver.PrintData(&m_saved);
}

String aiSuitLengthSolver.PrintWorkingData()
{
    return aiSuitLengthSolver.PrintData(&m_working);
}

String aiSuitLengthSolver.PrintData(slData *data)
{
	String out;
	int i = 0;
	int j = 0;

	out.Empty();
	// Debug log output text is prefixed with "Debug :". Hence add a new line so that the first line is
	// aligned with the rest
	out.Append(("\n"));
	if(!data)
	{
		.wxLogError(String.Format(("Input variable data is null. %s:%d"),
			(__FILE__), __LINE__));
		return out;
	}

	// Print the header or the table
	// S1    |S2    |S3    |S4
	// ------+------+------+------

	out.Append(("       "));
	for(i = 0; i < slTOTAL_SUITS; i++)
	{
		out.Append(String.Format(("S%d    |"), i + 1));
    }

    out.Append(("TotLen|Allocd|SumMax|SumMin"));
    out.Append(("\n"));
    out.Append(("------+------+------+------+------+------+------+------+------\n"));

    // Print the rest of the table

    for(i = 0; i < slTOTAL_HANDS; i++)
    {
        // Print the first column
        // H1    |
        // ------+
        // H2    |

        out.Append(String.Format(("H%d    |"), i));

        // Print cells for each hand in the format <suit_length>(<min>, <max>)

        for(j = 0; j < slTOTAL_SUITS; j++)
        {
            // If the cell is vacant, an "x" is printed instead of the numberic value of slVACANT

            if(data.cells[i][j].suit_length == slVACANT)
            {
                out.Append(("x"));
            }
            else
            {
                out.Append(String.Format(("%d"), data.cells[i][j].suit_length));
            }
            out.Append(String.Format(("(%d,%d)|"),
                                        data.cells[i][j].min, data.cells[i][j].max
                                        ));
        }

        // Print data for the following columns
        // TotLen|Allocd|SumMax

        out.Append(String.Format(("%6d|"), data.hand_total_length[i]));
        out.Append(String.Format(("%6d|"), data.hand_allocated[i]));
        out.Append(String.Format(("%6d|"), data.hand_sum_of_maxs[i]));
        out.Append(String.Format(("%6d"), data.hand_sum_of_vacant_mins[i]));
        out.Append(("\n"));
        out.Append(("------+------+------+------+------+------+------+------+------\n"));

    }

    // Print data for the following rows
    // TotLen|...
    // ------+...
    // Allocd|...
    // ------+...
    // SumMax|...
    // ------+...
    // SumMin|...

    out.Append(("TotLen|"));
    for(i = 0; i < slTOTAL_SUITS; i++)
    {
        out.Append(String.Format(("%6d|"), data.suit_total_length[i]));
    }
    out.Append(("\n"));
    out.Append(("------+------+------+------+------+\n"));

    out.Append(("Allocd|"));
    for(i = 0; i < slTOTAL_SUITS; i++)
    {
        out.Append(String.Format(("%6d|"), data.suit_allocated[i]));
    }
    out.Append(("\n"));
    out.Append(("------+------+------+------+------+\n"));

    out.Append(("SumMax|"));
    for(i = 0; i < slTOTAL_SUITS; i++)
    {
        out.Append(String.Format(("%6d|"), data.suit_sum_of_maxs[i]));
    }
    out.Append(("\n"));
    out.Append(("------+------+------+------+------+\n"));

    out.Append(("SumMin|"));
    for(i = 0; i < slTOTAL_SUITS; i++)
    {
        out.Append(String.Format(("%6d|"), data.suit_sum_of_vacant_mins[i]));
    }
    out.Append(("\n"));

    return out;
}

String aiSuitLengthSolver.PrintMatrix(slMatrix matrix)
{
    String out;
	int i = 0;
	int j = 0;

	out.Empty();
	out.Append(("\n"));

	if(!matrix)
	{
		.wxLogError(String.Format(("Input variable matrix is null. %s:%d"),
			(__FILE__), __LINE__));
		return out;
	}

	// Print the header or the table
	// S1|S2|S3|S4
	// --+--+--+--

	out.Append(("   "));
	for(i = 0; i < slTOTAL_SUITS; i++)
	{
		out.Append(String.Format(("S%d"), i + 1));
		if(i != (slTOTAL_SUITS - 1))
		{
		    out.Append(("|"));
		}
    }
    out.Append(("\n"));
    out.Append(("--+--+--+--+--\n"));

    for(i = 0; i < slTOTAL_HANDS; i++)
    {
        // Print the first column
        // H1|
        // --+
        // H2|

        out.Append(String.Format(("H%d|"), i));

        // Print cells for each hand in the format <suit_length>(<min>, <max>)

        for(j = 0; j < slTOTAL_SUITS; j++)
        {
            out.Append(String.Format(("%2d"), matrix[i][j]));
            if(j != (slTOTAL_SUITS - 1))
            {
                out.Append(("|"));
            }
        }
        out.Append(("\n"));
        if(i != (slTOTAL_HANDS - 1))
        {
            out.Append(("--+--+--+--+--\n"));
        }

    }

    return out;
}
