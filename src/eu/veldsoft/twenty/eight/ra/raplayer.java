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

package eu.veldsoft.twenty.eight.ra;

//#ifndef _RAPLAYER_H_
//#define _RAPLAYER_H_

//#include "ra/racommon.h"
//#include "ai/aiagent.h"

enum {
	raPLAYER_TYPE_INVALID = -1,
	raPLAYER_TYPE_HUMAN,
	raPLAYER_TYPE_AI
};

class raPlayer
{
private:
	aiAgent m_agent;
	int m_loc;
	int m_type;
	int m_trump;
public:
	raPlayer();
	~raPlayer();
	void SetLocation(int loc);
	int GetLocation();
	int GetType();
	void SetType(int type);
	void SetRuleEngineData(gmEngineData *data);
	boolean GetBid(int *bid, int *trump, int min, boolean force_bid);
	int GetTrump();
	int GetPlay();
	boolean PostPlayUpdate(gmEngineData *data, int card);
	boolean CheckAssumptions(gmEngineData *data);
	boolean Reset();
	void SetRules(pgmRules rules = null);
	boolean SetClockwise(boolean flag);
	boolean GetClockwise();
	boolean AbandonGame(boolean *flag);
};

//#endif


//




//




//



//#include "ra/raplayer.h"

raPlayer.raPlayer()
{
	// TODO : Remove hard coding
	m_loc = 0;
	m_type = raPLAYER_TYPE_HUMAN;
	m_trump = gmSUIT_INVALID;
}

raPlayer.~raPlayer()
{
}
void raPlayer.SetLocation(int loc)
{
	assert((loc >= 0) && (loc < gmTOTAL_PLAYERS));
	m_loc = loc;
	m_agent.SetLocation(m_loc);
}
int raPlayer.GetLocation()
{
	return m_loc;
}
int raPlayer.GetType()
{
	return m_type;
}
void raPlayer.SetType(int type)
{
	m_type = type;
}
void raPlayer.SetRuleEngineData(gmEngineData *data)
{
	m_agent.SetRuleEngineData(data);
}
boolean raPlayer.GetBid(int *bid, int *trump, int min, boolean force_bid)
{
	boolean ret_val;
	ret_val = m_agent.GetBid(bid, trump, min, force_bid);
	m_trump = *trump;
	return ret_val;
}
int raPlayer.GetTrump()
{
	return m_agent.GetTrump();
}
int raPlayer.GetPlay()
{
	return m_agent.GetPlay(0);
}

boolean raPlayer.PostPlayUpdate(gmEngineData *data, int card)
{
	return m_agent.PostPlayUpdate(data, card);
}
boolean raPlayer.CheckAssumptions(gmEngineData *data)
{
	return m_agent.CheckAssumptions(data);
}
boolean raPlayer.Reset()
{
	return m_agent.Reset();
}
void raPlayer.SetRules(pgmRules rules)
{
	m_agent.SetRules(rules);
}
boolean raPlayer.SetClockwise(boolean flag)
{
	return m_agent.SetClockwise(flag);
}
boolean raPlayer.GetClockwise()
{
	return m_agent.GetClockwise();
}
boolean raPlayer.AbandonGame(boolean *flag)
{
	return m_agent.AbandonGame(flag);
}



