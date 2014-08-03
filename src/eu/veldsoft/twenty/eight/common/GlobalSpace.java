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

package eu.veldsoft.twenty.eight.common;

import java.util.Random;

import eu.veldsoft.twenty.eight.ai.*;
import eu.veldsoft.twenty.eight.gg.*;
import eu.veldsoft.twenty.eight.gm.*;
import eu.veldsoft.twenty.eight.ra.*;

public class GlobalSpace {
	public static final class Color extends android.graphics.Color {
		public Color(int r, int g, int b) {
		}
	}

	public static final class wxApp {
		public wxFrame GetTopWindow() {
			return null;
		}
	}

	public static final class wxFrame {
		public wxStatusBar GetStastusBar() {
			return null;
		}
	}

	public static final class wxStatusBar {
	}

	public static final Random PRNG = new Random();

	public static wxApp wxTheApp;

	public static final int aiBID_SAMPLE = 100;
	public static final int aiPLAY_SAMPLES = 30;
	public static final int aiMAX_MOVES = 20;
	public static final int aiGENMV_NOTRUMP = 1;
	public static final int aiGENMV_TRUMP = 2;
	public static final int aiGENMV_ALL = 3;

	public static final int aiPOS_INFTY = +10000;
	public static final int aiNEG_INFTY = -10000;

	// public static final int aiLOG_GENERATESLPROBLEM =1;
	// public static final int aiLOG_GENERATEDEALS =0;

	public static final int slLENGTH_MAX = 8;
	public static final int slTOTAL_HANDS = 4;
	public static final int slTOTAL_SUITS = 4;
	public static final int slVACANT = -1;

	public static final int GG_CARD_CARD_COUNT = 52;

	public static final int GG_CARD_SPADES = 3;
	public static final int GG_CARD_HEARTS = 2;
	public static final int GG_CARD_DIAMONDS = 1;
	public static final int GG_CARD_CLUBS = 0;

	public static final int GG_CARD_ACE = 0;
	public static final int GG_CARD_TWO = 1;
	public static final int GG_CARD_THREE = 2;
	public static final int GG_CARD_FOUR = 3;
	public static final int GG_CARD_FIVE = 4;
	public static final int GG_CARD_SIX = 5;
	public static final int GG_CARD_SEVEN = 6;
	public static final int GG_CARD_EIGHT = 7;
	public static final int GG_CARD_NINE = 8;
	public static final int GG_CARD_TEN = 9;
	public static final int GG_CARD_JACK = 10;
	public static final int GG_CARD_QUEEN = 11;
	public static final int GG_CARD_KING = 12;

	public static final int GG_CARD_BACK_1 = 20;
	public static final int GG_CARD_BACK_2 = 21;
	public static final int GG_CARD_JOKER_1 = 30;
	public static final int GG_CARD_JOKER_2 = 31;

	public static final int GG_CARD_TOTAL_SUITS = 4;
	public static final int GG_CARD_TOTAL_VALUES = 13;

	public static final int GG_CARD_WIDTH = 71;
	public static final int GG_CARD_HEIGHT = 96;

	public static final String GG_CARD_XRS = ("cards.xrs");

	public static final int gmRULE_1 = 1;
	public static final int gmRULE_2 = 2;
	public static final int gmRULE_3 = 4;
	public static final int gmRULE_4 = 8;
	// Sluffing of jacks
	public static final int gmRULE_5 = 16;

	public static final int gmDEAL_ROUND_1 = 0;
	public static final int gmDEAL_ROUND_2 = 1;

	public static final int raBID_ROUND_3 = 2;

	public static final int gmFOUR_JACKS = 0x80808080;
	public static final int gmJACK = 0x80;
	public static final int gmALL_CARDS = 0xFFFFFFFF;

	public static final int gmNext(int X, gmEngineData m_data) {
		return ((X + m_data.rules.rot_addn) % gmTOTAL_PLAYERS);
	}

	public static final int gmTrickNext(gmEngineData m_data) {
		return ((m_data.tricks[m_data.trick_round].lead_loc + (m_data.tricks[m_data.trick_round].count * m_data.rules.rot_addn)) % 4);
	}

	public static final int gmWinnerCard(gmEngineData m_data) {
		return (m_data.tricks[m_data.trick_round].cards[m_data.tricks[m_data.trick_round].winner]);
	}

	public static final String SPACES20 = ("                    ");

	public static final int gmTOTAL_CARDS = 32;
	public static final int gmTOTAL_PLAYERS = 4;
	public static final int gmTOTAL_BID_ROUNDS = 3;
	public static final int gmPLAYER_INVALID = -1;
	public static final int gmSUIT_INVALID = -1;
	public static final int gmCARD_INVALID = -1;

	public static final int gmPartner(int X) {
		return ((X + 2) % gmTOTAL_PLAYERS);
	}

	public static final int gmBID_PASS = 0;
	public static final int gmBID_ALL = 1000;
	public static final int gmTOTAL_SUITS = 4;
	public static final int gmTOTAL_VALUES = 8;
	public static final int gmTOTAL_TRICKS = 8;
	public static final int gmTOTAL_TEAMS = 2;

	public static final int gmGetSuit(int X) {
		return (X / gmTOTAL_VALUES);
	}

	public static final int gmGetValue(int X) {
		return (X % gmTOTAL_VALUES);
	}

	public static final int gmGetTeam(int X) {
		return (X % gmTOTAL_TEAMS);
	}

	public static final int gmGetOpponent(int X) {
		return ((X + 1) % gmTOTAL_TEAMS);
	}

	public static final int gmGetPartner(int X) {
		return ((X + 2) % gmTOTAL_PLAYERS);
	}

	public static final int gmGetOpponentOne(int X) {
		return ((X + 1) % gmTOTAL_PLAYERS);
	}

	public static final int gmGetOpponentTwo(int X) {
		return ((X + 3) % gmTOTAL_PLAYERS);
	}

	// public static final raGET_CARD_INDEX(crd) ((crd.GetSuit() << 3) +
	// gmUtil.m_value_trans[crd.GetValue()])

	// Calculates the total number of points in a hand
	public static final int gmTotalPoints(int X) {
		return (gmUtil.m_total_points[(gmUtil.m_suit_mask[0] & X) >> gmUtil.m_suit_rs[0]]
				+ gmUtil.m_total_points[(gmUtil.m_suit_mask[1] & X) >> gmUtil.m_suit_rs[1]]
				+ gmUtil.m_total_points[(gmUtil.m_suit_mask[2] & X) >> gmUtil.m_suit_rs[2]] + gmUtil.m_total_points[(gmUtil.m_suit_mask[3] & X) >> gmUtil.m_suit_rs[3]]);
	}

	public static final int raBID_BTN_ROWS = 5;
	public static final int raBID_BTN_COLS = 3;
	public static final int raBID_TOTAL_BTNS = 15;

	public static final int raBID_BTN_ID_START = 100;
	public static final int raBID_BTN_ID_MAX = (raBID_BTN_ID_START
			+ raBID_TOTAL_BTNS - 1);
	public static final int raBID_BTN_ID_ALL = 150;
	public static final int raBID_BTN_ID_PASS = 151;

	public static final int raBID_MIN_BTN_WIDTH = 10;

	public static final int raGetBidFromId(int X) {
		return (X - raBID_BTN_ID_START + 14);
	}

	public static final int raBID_PNL_RELIEF = 2;

	public static final String RA_APP_MAJOR_VER = ("1");
	public static final String RA_APP_MINOR_VER = ("0");
	public static final String RA_APP_REL_TYPE = ("b");
	public static final String RA_APP_REL_TYPE_VER = ("1");
	public static final String RA_APP_NAME = ("Rosanne");
	public static final String RA_APP_AUTHOR = ("Vipin Cherian");

	public static final String RA_APP_FULL_VER = RA_APP_MAJOR_VER + (".")
			+ RA_APP_MINOR_VER + RA_APP_REL_TYPE + RA_APP_REL_TYPE_VER;

	public static final String RA_APP_FULL_NAME = RA_APP_NAME + (" ")
			+ RA_APP_FULL_VER;

	public static final String ra_APP_URL = ("http://rosanne.sourceforge.net");

	public static final int raBID_INVALID = -2;

	public static final int gen_rand32() {
		return (PRNG.nextInt(Integer.MAX_VALUE));
	}

	public static final int raGetRandPlayer() {
		return (gen_rand32() % gmTOTAL_PLAYERS);
	}

	// Colours
	public static final Color raCLR_HEAD_DARK = (new Color(0, 92, 133));
	public static final Color raCLR_HEAD_LITE = (new Color(136, 219, 255));

	public static final Color raCLR_BLUE_DARK = new Color(129, 203, 255);
	public static final Color raCLR_BLUE_LITE = new Color(168, 219, 255);
	public static final Color raCLR_PURP_DARK = new Color(188, 174, 255);
	public static final Color raCLR_PURP_LITE = new Color(212, 204, 255);

	public static final Color raCLR_BUBB_FILL = new Color(173, 255, 135);

	// public static final Color raCLR_INSTR Color(144, 0, 255);
	public static final Color raCLR_INSTR = new Color(255, 0, 144);
	// public static final Color raCLR_INSTR (*wxRED);

	public static final int raARROW_WIDTH = 16;
	public static final int raBUBB_ARROW_WIDTH = 12;
	// public static final int raBUBB_EDGE_WIDTH =5;
	// public static final int raBUBB_CORNER_WIDTH =5;
	public static final int raBUBB_UNIT_MIN = 5;
	public static final int raBUBB_MIN_WIDTH = 120;
	public static final int raBUBB_MIN_HEIGHT = 40;
	public static final int raBUBB_ARROW_OVERLAP = 3;
	public static final int raBUBB_ARROW_PROTUN = (raBUBB_ARROW_WIDTH - raBUBB_ARROW_OVERLAP);

	public static final wxStatusBar raStatusBar() {
		return ((wxFrame) (wxTheApp.GetTopWindow())).GetStastusBar();
	}

	public static final String raTEXT_CLOCKWISE = ("Clockwise");
	public static final String raTEXT_ANTICLOCKWISE = ("Anti-lockwise");

	public static final String raCONFPATH_APP_DATA_X = ("application/x");
	public static final String raCONFPATH_APP_DATA_Y = ("application/y");
	public static final String raCONFPATH_APP_DATA_WIDTH = ("application/width");
	public static final String raCONFPATH_APP_DATA_HEIGHT = ("application/height");
	public static final String raCONFPATH_APP_DATA_MAX = ("application/maximized");

	public static final String raCONFPATH_GAME_DATA_CLOCK = ("game/clockwise");
	public static final String raCONFPATH_GAME_DATA_MINBID3 = ("game/minbid3");
	public static final String raCONFPATH_GAME_DATA_WAIVERULE4 = ("game/waiverule4");
	public static final String raCONFPATH_GAME_DATA_SLUFFJACKS = ("game/sluffjacks");

	public static final String raCONFPATH_PREFS_PLAYCARDON = ("preferences/playcardon");
	public static final String raCONFPATH_PREFS_CARDBACK = ("preferences/cardback");
	public static final String raCONFPATH_PREFS_AUTOPLAYSINGLE = ("preferences/autoplaysingle");
	public static final String raCONFPATH_PREFS_BIDBUBBLES = ("preferences/bidbubbles");

	public static final int raCONFIG_PREFS_PLAYCARDON_SCLICK = 0;
	public static final int raCONFIG_PREFS_PLAYCARDON_DCLICK = 1;
	public static final int raCONFIG_PREFS_CARDBACK_BLUE = 0;
	public static final int raCONFIG_PREFS_CARDBACK_RED = 1;

	public static final int raPREFS_PLAYCARDON_SCLICK = 0;
	public static final int raPREFS_PLAYCARDON_DCLICK = 1;
	public static final int raPREFS_CARDBACK_BLUE = 0;
	public static final int raPREFS_CARDBACK_RED = 1;

	// public static final int raGAME_PLAY_TILL_END =0;

	public static final int raTOTAL_CARD_BACKS = 2;
	public static final int raMAX_CARDS_PER_HAND = 8;
	public static final int raCARD_VERT_RELIEF = (12);
	public static final int raCARD_HORZ_RELIEF = (GG_CARD_WIDTH / 4);
	public static final int raCARD_PANEL_RELIEF = 20;

	public static final int raGAME_CARD_BACK_SEL = 0;

	public static final int raGAME_ARROW_RELIEF = 8;

	public static final int raGAME_FOUR_JACKS = (0x80808080);
	public static final int raGAME_ALL_LOW_CARDS = (0x0F0F0F0F);
	public static final int raGAME_ALL_HIGH_CARDS = (0xF0F0F0F0);

	// For testing purposes
	public static final String raTEST_DATA_FILE = ("ra_test_data.ini");
	public static final String raTEXT_SEED = ("rand/seed");
	public static final String raTEXT_IDX = ("rand/idx");
	public static final String raTEXT_DEALER = ("deal/dealer");
	public static final String raTEXT_DEAL_ROUND = ("deal_round");

	public static final String raINFO_SHOW_TRUMP_TEXT = ("Show Trump");
	public static final String raINFO_DEAL_TEXT = ("New Deal");

	public static final int raSBAR_FIELDS = 2;

	public static final String raLOG_DIR = ("log");
	public static final String raLOG_FILE_PREFIX = ("rosanne");
	public static final String raLOG_FILE_EXTN = ("log");
	public static final String raLOG_FILE_DELIM = ("_");

	public static final String raGUI_XRS = ("gui.xrs");

	public static final String raUPDATE_VER = ("1");
	public static final String raUPDATE_URL = ("http://rosanne.sourceforge.net/ver.txt");
	// public static final String raUPDATE_APPURL
	// "http://rosanne.sourceforge.net"
}
