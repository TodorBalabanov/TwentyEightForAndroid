

//




//




//



package eu.veldsoft.twenty.eight.gm;

//#ifndef _GMUTIL_H_
//#define _GMUTIL_H_

//#include "wx/wxprec.h"

//#ifdef __BORLANDC__
//#pragma hdrstop
//#endif

//#ifndef WX_PRECOMP
//#include "wx/wx.h"
//#endif

//#define SPACES20 _("                    ")

//#define gmTOTAL_CARDS 32
//#define gmTOTAL_PLAYERS 4
//#define gmTOTAL_BID_ROUNDS 3
//#define gmPLAYER_INVALID -1
//#define gmSUIT_INVALID -1
//#define gmCARD_INVALID -1
//#define gmPartner(X) ((X + 2) % gmTOTAL_PLAYERS)

//#define gmBID_PASS 0
//#define gmBID_ALL 1000
//#define gmTOTAL_SUITS 4
//#define gmTOTAL_VALUES 8
//#define gmTOTAL_TRICKS 8
//#define gmTOTAL_TEAMS 2

//#define gmGetSuit(X) (X / gmTOTAL_VALUES)
//#define gmGetValue(X) (X % gmTOTAL_VALUES)
//#define gmGetTeam(X) (X % gmTOTAL_TEAMS)
//#define gmGetOpponent(X) ((X + 1) % gmTOTAL_TEAMS)
//#define gmGetPartner(X) ((X + 2) % gmTOTAL_PLAYERS)
//#define gmGetOpponentOne(X) ((X + 1) % gmTOTAL_PLAYERS)
//#define gmGetOpponentTwo(X) ((X + 3) % gmTOTAL_PLAYERS)

class gmUtil
{
public:
	static int m_value_trans[];
	static void ShuffleArray(int *array, long n);
	static String PrintLong(long cards);
	static String PrintHands(long *hands);
	static String m_suits[];
	static String m_values[];
	static String m_short_locs[];
	static String m_long_locs[];
	static long m_suit_mask[];
	static long m_suit_rs[];
	static int m_points[];
	static int m_total_points[];
	static String m_short_teams[];
	static int GetCardIndex(String text);
	static boolean SetStatusText(final String& text, int i = 0);
	static int BitsSetTable256[];
	static int LogTable256[];
	static long CountBitsSet(long v);
	static long HighestBitSet(long v);
};

////#define raGET_CARD_INDEX(crd) ((crd.GetSuit() << 3) + gmUtil.m_value_trans[crd.GetValue()])

// Calculates the total number of points in a hand
//#define gmTotalPoints(X) ( \
gmUtil.m_total_points[(gmUtil.m_suit_mask[0] & X) >> gmUtil.m_suit_rs[0]] + \
gmUtil.m_total_points[(gmUtil.m_suit_mask[1] & X) >> gmUtil.m_suit_rs[1]] + \
gmUtil.m_total_points[(gmUtil.m_suit_mask[2] & X) >> gmUtil.m_suit_rs[2]] + \
gmUtil.m_total_points[(gmUtil.m_suit_mask[3] & X) >> gmUtil.m_suit_rs[3]]  \
)

//#endif



//




//




//




// Part of the code borrowed from
// http://graphics.stanford.edu/~seander/bithacks.html

//#include "gm/gmutil.h"
//#include "SFMT.h"
//#include "SFMT-params.h"

//int gmUtil.m_value_trans[] = {5, -1, -1, -1, -1, -1, 0, 1, 6, 4, 7, 2, 3};
int gmUtil.m_value_trans[] = {6, 7, 11, 12, 9, 0, 8, 10};
String gmUtil.m_suits[] = {("C"), ("D"), ("H"), ("S")};
String gmUtil.m_values[] = {("7"), ("8"), ("Q"), ("K"), ("10"), ("A"), ("9"), ("J")};
String gmUtil.m_short_locs[] = {("S"), ("W"), ("N"), ("E")};
String gmUtil.m_long_locs[] = {("South"), ("West"), ("North"), ("East")};
long gmUtil.m_suit_mask[] = {0x000000FF, 0x0000FF00, 0x00FF0000, 0xFF000000};
long gmUtil.m_suit_rs[] = {0, 8, 16, 24};
int gmUtil.m_points[] = {0, 0, 0, 0, 1, 1, 2, 3};
int gmUtil.m_total_points[] = {
	0, 0, 0, 0, 0, 0, 0, 0,
0, 0, 0, 0, 0, 0, 0, 0,
1, 1, 1, 1, 1, 1, 1, 1,
1, 1, 1, 1, 1, 1, 1, 1,
1, 1, 1, 1, 1, 1, 1, 1,
1, 1, 1, 1, 1, 1, 1, 1,
2, 2, 2, 2, 2, 2, 2, 2,
2, 2, 2, 2, 2, 2, 2, 2,
2, 2, 2, 2, 2, 2, 2, 2,
2, 2, 2, 2, 2, 2, 2, 2,
3, 3, 3, 3, 3, 3, 3, 3,
3, 3, 3, 3, 3, 3, 3, 3,
3, 3, 3, 3, 3, 3, 3, 3,
3, 3, 3, 3, 3, 3, 3, 3,
4, 4, 4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4, 4, 4,
3, 3, 3, 3, 3, 3, 3, 3,
3, 3, 3, 3, 3, 3, 3, 3,
4, 4, 4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4, 4, 4,
4, 4, 4, 4, 4, 4, 4, 4,
5, 5, 5, 5, 5, 5, 5, 5,
5, 5, 5, 5, 5, 5, 5, 5,
5, 5, 5, 5, 5, 5, 5, 5,
5, 5, 5, 5, 5, 5, 5, 5,
6, 6, 6, 6, 6, 6, 6, 6,
6, 6, 6, 6, 6, 6, 6, 6,
6, 6, 6, 6, 6, 6, 6, 6,
6, 6, 6, 6, 6, 6, 6, 6,
7, 7, 7, 7, 7, 7, 7, 7,
7, 7, 7, 7, 7, 7, 7, 7
};
String gmUtil.m_short_teams[] = {("N/S"), ("E/W"), ("N/S"), ("E/W")};

int gmUtil.BitsSetTable256[] =
{
	0, 1, 1, 2, 1, 2, 2, 3, 1, 2, 2, 3, 2, 3, 3, 4,
		1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
		1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
		2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
		1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
		2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
		2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
		3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
		1, 2, 2, 3, 2, 3, 3, 4, 2, 3, 3, 4, 3, 4, 4, 5,
		2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
		2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
		3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
		2, 3, 3, 4, 3, 4, 4, 5, 3, 4, 4, 5, 4, 5, 5, 6,
		3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
		3, 4, 4, 5, 4, 5, 5, 6, 4, 5, 5, 6, 5, 6, 6, 7,
		4, 5, 5, 6, 5, 6, 6, 7, 5, 6, 6, 7, 6, 7, 7, 8
};

int gmUtil.LogTable256[] =
{
	0, 0, 1, 1, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3,
		4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4,
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
		5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5,
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
		6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6, 6,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7,
		7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7, 7
};

//Fisher-Yates shuffle
//http://en.wikipedia.org/wiki/Fisher%E2%80%93Yates_shuffle
//
//(For a zero-based array)
//To shuffle an array a of n elements:
//   for i from n - 1 downto 1 do
//         j ? random integer with 0 <= j <= i
//         exchange a[j] and a[i]

void gmUtil.ShuffleArray(int *array, long n)
{

	if (n > 1) {
		long i;
		for (i = n - 1; i >= 1; i--) {
		    // TODO : Non linear distribution. To be corrected.
			long j = ((long)gen_rand32()) % (i + 1);
			int t = array[j];
			array[j] = array[i];
			array[i] = t;
		}
	}
}

String gmUtil.PrintLong(long cards)
{
	long i;
	String out, final;

	//wxLogDebug(String.Format("Cards = %lu", cards));

	out = _("");
	//wxLogDebug(String.Format("%s%s", m_suits[highest / 8], m_values[highest % 8]));
	for(i = 0; i < 32; i++)
	{
		if(cards & (1 << i))
			out = out + m_suits[i / 8] + m_values[i % 8] + _(",");
	}
	if(!out.IsEmpty())
	{
		final = out.Left(out.Length() - 1);
	}

	return final;
}
String gmUtil.PrintHands(long *hands)
{
	String ret_val;
	String final;
	String out;
	long temp;
	int i, j;

	ret_val.Clear();

	// Print North first
	//wxLogMessage(SPACES20 + m_long_locs[2]);
	ret_val.Append(SPACES20 + m_long_locs[2]);
	ret_val.Append(("\n"));

	for (i = 0; i < 4; i++)
	{
		temp = (hands[2] & m_suit_mask[i]) >> m_suit_rs[i];
		out = String.Format(("%s - "), m_suits[i].c_str());
		for(j = 7; j >= 0; j--)
		{
			if(temp & (1 << j))
				out = out + m_values[j % 8] + _(",");
		}

		final = out.Left(out.Length() - 1);
		//wxLogMessage(SPACES20 + final);
		ret_val.Append(SPACES20 + final);
		ret_val.Append(("\n"));
	}

	// Print East and West in the same line :D
	//wxLogMessage(String.Format("%-40s%-40s", m_long_locs[1], m_long_locs[3]));
	ret_val.Append(String.Format(("%-40s%-40s"), m_long_locs[1].c_str(), m_long_locs[3].c_str()));
	ret_val.Append(("\n"));

	for (i = 0; i < 4; i++)
	{
		temp = (hands[1] & m_suit_mask[i]) >> m_suit_rs[i];
		out = String.Format(("%s - "), m_suits[i].c_str());
		//for(j = 0; j < 8; j++)
		for(j = 7; j >= 0; j--)
		{
			if(temp & (1 << j))
				out = out + m_values[j % 8] + _(",");
		}

		final = String.Format(("%-40s"), out.Left(out.Length() - 1).c_str());

		temp = (hands[3] & m_suit_mask[i]) >> m_suit_rs[i];
		out = String.Format(("%s - "), m_suits[i].c_str());
		//for(j = 0; j < 8; j++)
		for(j = 7; j >= 0; j--)
		{
			if(temp & (1 << j))
				out = out + m_values[j % 8] + _(",");
		}

		final += String.Format(("%-40s"), out.Left(out.Length() - 1).c_str());

		//wxLogMessage(final);
		ret_val.Append(final);
		ret_val.Append(("\n"));
	}

	// Finally print South
	//wxLogMessage(SPACES20 + m_long_locs[0]);
	ret_val.Append(SPACES20 + m_long_locs[0]);
	ret_val.Append(("\n"));

	for (i = 0; i < 4; i++)
	{
		temp = (hands[0] & m_suit_mask[i]) >> m_suit_rs[i];
		out = String.Format(("%s - "), m_suits[i].c_str());
		//for(j = 0; j < 8; j++)
		for(j = 7; j >= 0; j--)
		{
			if(temp & (1 << j))
				out = out + m_values[j % 8] + _(",");
		}

		final = out.Left(out.Length() - 1);
		//wxLogMessage(SPACES20 + final);
		ret_val.Append(SPACES20 + final);
		ret_val.Append(("\n"));
	}
	return ret_val;
}

int gmUtil.GetCardIndex(String text)
{
	String suit, value;
	int x, y;
	suit = text.Left(1);
	//wxPrintf(suit + "\n");
	value = text.Mid(1);
	//wxPrintf(value + "\n");

	if(!suit.CmpNoCase(("C")))
		x = 0;
	else if(!suit.CmpNoCase(("D")))
		x = 1;
	else if(!suit.CmpNoCase(("H")))
		x = 2;
	else if(!suit.CmpNoCase(("S")))
		x = 3;
	else
		return -1;

	if(!value.CmpNoCase(("7")))
		y = 0;
	else if(!value.CmpNoCase(("8")))
		y = 1;
	else if(!value.CmpNoCase(("Q")))
		y = 2;
	else if(!value.CmpNoCase(("K")))
		y = 3;
	else if(!value.CmpNoCase(("10")))
		y = 4;
	else if(!value.CmpNoCase(("A")))
		y = 5;
	else if(!value.CmpNoCase(("9")))
		y = 6;
	else if(!value.CmpNoCase(("J")))
		y = 7;
	else
		return -1;

	return (x * 8) + y;
}
boolean gmUtil.SetStatusText(final String& text, int i)
{
	wxFrame *main_frame;
	wxStatusBar *status_bar;

	main_frame = null;
	main_frame = (wxFrame *)wxTheApp.GetTopWindow();
	if(!main_frame)
		return false;

	status_bar = null;
	status_bar = main_frame.GetStatusBar();
	if(!status_bar)
		return false;

	status_bar.SetStatusText(text, i);
	status_bar.Update();
	return true;
}



long gmUtil.CountBitsSet(long v)
{
	return BitsSetTable256[v & 0xff] +
		BitsSetTable256[(v >> 8) & 0xff] +
		BitsSetTable256[(v >> 16) & 0xff] +
		BitsSetTable256[v >> 24];
	//int final w = v - ((v >> 1) & 0x55555555);                    // temp
	//int final x = (w & 0x33333333) + ((w >> 2) & 0x33333333);     // temp
	//int final c = ((x + (x >> 4) & 0xF0F0F0F) * 0x1010101) >> 24; // count
	//return c;
}

long gmUtil.HighestBitSet(long v)
{
	register long t, tt; // temporaries
	if (tt = v >> 16)
	{
		return (t = v >> 24) ? 24 + LogTable256[t] : 16 + LogTable256[tt & 0xFF];
	}
	else
	{
		return (t = v >> 8) ? 8 + LogTable256[t] : LogTable256[v];
	}
}

