

//




//




//



package eu.veldsoft.twenty.eight.ra;

//#ifndef _RAGAME_H
//#define _RAGAME_H

//#include "ra/racommon.h"
//#include "gm/gmengine.h"
//#include "ra/rainfo.h"
//#include "ra/raevents.h"
//#include "ra/rabid.h"
//#include "ra/raplayer.h"
//#include "ra/raconfig.h"
//#include "gg/ggpanel.h"
//#include "gg/ggcard.h"

////#define raGAME_PLAY_TILL_END 0

//#define raTOTAL_CARD_BACKS 2
//#define raMAX_CARDS_PER_HAND 8
//#define raCARD_VERT_RELIEF (12)
//#define raCARD_HORZ_RELIEF (GG_CARD_WIDTH / 4)
//#define raCARD_PANEL_RELIEF 20

//#define raGAME_CARD_BACK_SEL 0

//#define raGAME_ARROW_RELIEF 8

//#define raGAME_FOUR_JACKS (0x80808080)
//#define raGAME_ALL_LOW_CARDS (0x0F0F0F0F)
//#define raGAME_ALL_HIGH_CARDS (0xF0F0F0F0)


// For testing purposes
//#define raTEST_DATA_FILE ("ra_test_data.ini")
//#define raTEXT_SEED ("rand/seed")
//#define raTEXT_IDX ("rand/idx")
//#define raTEXT_DEALER ("deal/dealer")
//#define raTEXT_DEAL_ROUND ("deal_round")



enum{
	raHAND_VERTICAL = 0,
	raHAND_HORIZONTAL
};

enum{
	raGAME_ORIENT_ALL_HORZ = 0,
	raGAME_ORIENT_MIXED
};

// TODO : If raHandCard is no longer used, remove
class tagRA_HAND_CARD
{
	//int suit;
	//int value;
	int index;
	int x;
	int y;
	int width;
	int height;
}raHandCard, *praHandCard;

class tagRA_HAND
{
	long cards;
	int count;
	//raHandCard card_info[raMAX_CARDS_PER_HAND];
	int card_indexes[raMAX_CARDS_PER_HAND];
}raHand, *praHand;

class tagRA_BACK_DRAW_INFO
{
	boolean draw_bid;
	int bid_loc;
	int bid;
}raBackDrawInfo;

class raGamePanel: public ggPanel
{
private:
	wxBitmap *m_tile;
	DECLARE_EVENT_TABLE()

	// Disallow copy constructor/assignment operators
	raGamePanel(final raGamePanel &);
    raGamePanel & operator=(final raGamePanel &);

	wxBitmap *m_card_faces[gmTOTAL_CARDS];
	wxBitmap *m_card_backs[raTOTAL_CARD_BACKS];
	gmEngine m_engine;
	raInfo *m_info;
	raBid *m_bid;

	// Related to the display of hands
	raHand m_hands[gmTOTAL_PLAYERS];
	wxRect m_hand_rects[gmTOTAL_PLAYERS];
	wxRect m_hand_card_rects[gmTOTAL_PLAYERS][raMAX_CARDS_PER_HAND];
	int m_hand_rot;

	// Related to the display of cards played in a trick
	//int m_trick_cards[gmTOTAL_PLAYERS];
	gmTrick m_trick;
	wxRect m_trick_card_rects[gmTOTAL_PLAYERS];
	boolean m_wait_trick;

	// To check whether the deal is still being played
	boolean m_deal_ended;

	// Players
	raPlayer m_players[gmTOTAL_PLAYERS];

	// Game points and penalties
	int m_game_pts[gmTOTAL_TEAMS];
	int m_pnlties[gmTOTAL_PLAYERS];

	// Direction of play
	boolean m_clockwise;

	// Font used to write text
	wxFont m_font_bold;

	// Hand orientation
	int m_orientation;

	// Prefereed card back
	int m_pref_card_back;

	// Play card on single click
	int m_play_card_on;

	// Auto play single choice
	boolean m_auto_play;

	// Show bid bubbles
	boolean m_show_bidbubbles;

	// Saved rules
	gmRules m_saved_rules;

	// Saved auction history
	String m_bid_history;

	// Bitmaps to be loaded from xpms

	wxBitmap *m_bmp_green_arrow_bottom;
	wxBitmap *m_bmp_green_arrow_top;
	wxBitmap *m_bmp_green_arrow_right;
	wxBitmap *m_bmp_green_arrow_left;

	wxBitmap *m_bmp_red_arrow_bottom;
	wxBitmap *m_bmp_red_arrow_top;
	wxBitmap *m_bmp_red_arrow_right;
	wxBitmap *m_bmp_red_arrow_left;

	wxBitmap *m_bmp_bubble_arrow_bottom;
	wxBitmap *m_bmp_bubble_arrow_left;
	wxBitmap *m_bmp_bubble_arrow_right;
	wxBitmap *m_bmp_bubble_arrow_top;

	wxBitmap *m_bmp_bubble_corner_ne;
	wxBitmap *m_bmp_bubble_corner_nw;
	wxBitmap *m_bmp_bubble_corner_se;
	wxBitmap *m_bmp_bubble_corner_sw;

	wxBitmap *m_bmp_bubble_edge_bottom;
	wxBitmap *m_bmp_bubble_edge_left;
	wxBitmap *m_bmp_bubble_edge_right;
	wxBitmap *m_bmp_bubble_edge_top;

	void OnSize(wxSizeEvent& event);
	boolean RedrawBack(raBackDrawInfo *info = null);
	boolean DrawHand(int loc, int x, int y, int orientation = raHAND_HORIZONTAL);
	boolean DrawTrick();
	boolean DrawTrump();
	void OnInfo(raInfoEvent& event);
	void OnBid(raBidEvent& event);
	void OnLeftDClick(wxMouseEvent &event);
	void OnLeftUp(wxMouseEvent &event);
	int GetCardAtPos(wxPoint pt, int loc = gmPLAYER_INVALID);
	int GetHandAtPos(wxPoint pt);
	boolean Continue();
	boolean ResetDeal();
	boolean ResetGame();
	boolean UpdateHands(long *hands);
	int PlayCard(int card, int loc = gmPLAYER_INVALID);
	int SetTrump(int card, int loc = gmPLAYER_INVALID);
	int ShowTrump(int loc = gmPLAYER_INVALID);
	int MakeBid(int bid, int loc = gmPLAYER_INVALID);
	boolean UpdateDrawAndRefresh(boolean udpate = true, raBackDrawInfo *info = null);
	//boolean UpdateTrick(gmTrick *trick);
	boolean HideInfo(gmEngineData *data, int player);
	boolean HasDealEnded(int *winner = null);
	boolean EndDeal(boolean abandon = false);
	boolean BeginBusyState();
	boolean EndBusyState();
	int CheckOppTrumps();
	boolean OnCardClick(wxPoint pt);
public:
	raGamePanel(final wxWindow* parent);
	virtual ~raGamePanel();
	boolean SetTile(wxBitmap *tile);
	boolean SetInfoPanel(raInfo *info_panel);
	boolean NewGame(int dealer = gmPLAYER_INVALID, boolean immediate = true);
	boolean NewDeal();
	boolean SetClockwise(boolean flag);
	boolean GetClockwise();
	boolean ReloadFromConfig();
	boolean ShowAuction();
	boolean ShowLastTrick();
};
//#endif


//




//




//




//#include "ra/ragamepanel.h"
//#include "wx/sstream.h"
//#include "gm/gmrand.h"

//#include "images/red_arrow_top.xpm"
//#include "images/red_arrow_bottom.xpm"
//#include "images/red_arrow_left.xpm"
//#include "images/red_arrow_right.xpm"

//#include "images/green_arrow_top.xpm"
//#include "images/green_arrow_bottom.xpm"
//#include "images/green_arrow_left.xpm"
//#include "images/green_arrow_right.xpm"

//#include "images/bubble_arrow_bottom.xpm"
//#include "images/bubble_arrow_left.xpm"
//#include "images/bubble_arrow_right.xpm"
//#include "images/bubble_arrow_top.xpm"

//#include "images/bubble_corner_ne.xpm"
//#include "images/bubble_corner_nw.xpm"
//#include "images/bubble_corner_se.xpm"
//#include "images/bubble_corner_sw.xpm"

//#include "images/bubble_edge_bottom.xpm"
//#include "images/bubble_edge_left.xpm"
//#include "images/bubble_edge_right.xpm"
//#include "images/bubble_edge_top.xpm"

//#define raGAME_HIDE_AI_HANDS 1
////#define raREAD_SEED_FROM_FILE 0
////#define raREAD_DEALER_FROM_FILE 0

//#if defined(raREAD_SEED_FROM_FILE) || defined(raREAD_DEALER_FROM_FILE)
    //#include <wx/wfstream.h>
    //#include <wx/fileconf.h>
//#endif

//
//int *psfmt32;
//int idxof(int);
//int idx;

BEGIN_EVENT_TABLE(raGamePanel, ggPanel)
	EVT_SIZE(raGamePanel.OnSize)
	EVT_INFO(raGamePanel.OnInfo)
	EVT_BID(raGamePanel.OnBid)
	EVT_LEFT_DCLICK(raGamePanel.OnLeftDClick)
	EVT_LEFT_UP(raGamePanel.OnLeftUp)
	//EVT_ERASE_BACKGROUND(wxcTable.OnErase)
END_EVENT_TABLE()

// TODO : For all wxMessageBox-es, provide appropriate heading, icon etc

//
// Constructor/s
//

raGamePanel.raGamePanel(final wxWindow* parent): ggPanel((wxWindow*)parent)
{
	// TODO : Consider each item and move to Reset if found appropriate
	int i, j;
	ggCard *card = null;

	m_hand_rot = 0;
	m_tile = null;
	m_info = null;
	m_bid = null;
	m_clockwise = true;
	m_pref_card_back = raCONFIG_PREFS_CARDBACK_BLUE;
	m_play_card_on = raCONFIG_PREFS_PLAYCARDON_SCLICK;
	m_auto_play = true;
	m_show_bidbubbles = true;


	// Set the properties of the bold font to be used to write the text
	// especially locations
	m_font_bold = wxSystemSettings.GetFont(wxSYS_DEFAULT_GUI_FONT);
	m_font_bold.SetWeight(wxFONTWEIGHT_BOLD);

	// Create bitmaps from xpms

	m_bmp_green_arrow_bottom = new wxBitmap(green_arrow_bottom_xpm);
	m_bmp_green_arrow_top = new wxBitmap(green_arrow_top_xpm);
	m_bmp_green_arrow_right = new wxBitmap(green_arrow_right_xpm);
	m_bmp_green_arrow_left = new wxBitmap(green_arrow_left_xpm);

	m_bmp_red_arrow_bottom = new wxBitmap(red_arrow_bottom_xpm);
	m_bmp_red_arrow_top = new wxBitmap(red_arrow_top_xpm);
	m_bmp_red_arrow_right = new wxBitmap(red_arrow_right_xpm);
	m_bmp_red_arrow_left = new wxBitmap(red_arrow_left_xpm);

	m_bmp_bubble_arrow_bottom = new wxBitmap(bubble_arrow_bottom_xpm);
	m_bmp_bubble_arrow_left = new wxBitmap(bubble_arrow_left_xpm);
	m_bmp_bubble_arrow_right = new wxBitmap(bubble_arrow_right_xpm);
	m_bmp_bubble_arrow_top = new wxBitmap(bubble_arrow_top_xpm);

	m_bmp_bubble_corner_ne = new wxBitmap(bubble_corner_ne_xpm);
	m_bmp_bubble_corner_nw = new wxBitmap(bubble_corner_nw_xpm);
	m_bmp_bubble_corner_se = new wxBitmap(bubble_corner_se_xpm);
	m_bmp_bubble_corner_sw = new wxBitmap(bubble_corner_sw_xpm);

	m_bmp_bubble_edge_bottom = new wxBitmap(bubble_edge_bottom_xpm);
	m_bmp_bubble_edge_left = new wxBitmap(bubble_edge_left_xpm);
	m_bmp_bubble_edge_right = new wxBitmap(bubble_edge_right_xpm);
	m_bmp_bubble_edge_top = new wxBitmap(bubble_edge_top_xpm);

	ResetGame();

	// Initiate the bitmap pointer array for card faces to null
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		m_players[i].SetLocation(i);
		m_players[i].SetType(raPLAYER_TYPE_AI);
		//m_players[i].SetType(raPLAYER_TYPE_HUMAN);
	}
	m_players[0].SetType(raPLAYER_TYPE_HUMAN);

	for(i = 0; i < gmTOTAL_CARDS; i++)
		m_card_faces[i] = null;

	// Initiate the bitmap pointer array for card backs to null
	for(i = 0; i < raTOTAL_CARD_BACKS; i++)
		m_card_backs[i] = null;

	// Load card faces
	for(i = 0; i < gmTOTAL_SUITS; i++)
	{
		for(j = 0; j < gmTOTAL_VALUES; j++)
		{
			card = new ggCard(i, gmUtil.m_value_trans[j]);
			assert(card);
			m_card_faces[(i * gmTOTAL_VALUES) + j] = new wxBitmap(*card.GetFace());
			if(!m_card_faces[(i * gmTOTAL_VALUES) + j])
			{
				wxLogError(String.Format(("Bitmap creation failed. %s:%d"), (__FILE__), __LINE__));
				return;
			}
			delete card;
			card = null;
		}
	}

	// Load card backs
	card = new ggCard(GG_CARD_BACK_1);
	assert(card);
	m_card_backs[0] = new wxBitmap(*card.GetFace());
	if(!m_card_backs[0])
	{
		wxLogError(String.Format(("Bitmap creation failed. %s:%d"), (__FILE__), __LINE__));
		return;
	}
	delete card;
	card = null;

	card = new ggCard(GG_CARD_BACK_2);
	assert(card);
	m_card_backs[1] = new wxBitmap(*card.GetFace());
	if(!m_card_backs[1])
	{
		wxLogError(String.Format(("Bitmap creation failed. %s:%d"), (__FILE__), __LINE__));
		return;
	}
	delete card;
	card = null;

	ReloadFromConfig();

	// TODO : Should DrawBack be called here?
	m_bid = new raBid(this);
	//m_bid.SetSize(2 * GG_CARD_WIDTH, 2 * GG_CARD_HEIGHT);
	//m_bid.SetWindowStyle(wxRAISED_BORDER);
	//m_bid.Refresh();
	m_bid.Show(false);
	m_bid.SetGamePanel(this);
}

//
// Destructor/s
//
raGamePanel.~raGamePanel()
{
	int i, j;

	// Delete card faces
	for(i = 0; i < gmTOTAL_SUITS; i++)
	{
		for(j = 0; j < gmTOTAL_VALUES; j++)
		{
			if(m_card_faces[(i * gmTOTAL_VALUES) + j])
				delete m_card_faces[(i * gmTOTAL_VALUES) + j];
			m_card_faces[(i * gmTOTAL_VALUES) + j] = null;
		}
	}

	// Delete card backs
	for(i = 0; i < raTOTAL_CARD_BACKS; i++)
	{
		if(m_card_backs[i])
			delete m_card_backs[i];
		m_card_backs[i] = null;
	}

	// Delete tile bitmap
	if(m_tile)
		delete m_tile;

	// Delete the bitmaps created from xpms

	if(m_bmp_green_arrow_bottom)
		delete m_bmp_green_arrow_bottom;
	if(m_bmp_green_arrow_top)
		delete m_bmp_green_arrow_top;
	if(m_bmp_green_arrow_right)
		delete m_bmp_green_arrow_right;
	if(m_bmp_green_arrow_left)
		delete m_bmp_green_arrow_left;

	if(m_bmp_red_arrow_bottom)
		delete m_bmp_red_arrow_bottom;
	if(m_bmp_red_arrow_top)
		delete m_bmp_red_arrow_top;
	if(m_bmp_red_arrow_right)
		delete m_bmp_red_arrow_right;
	if(m_bmp_red_arrow_left)
		delete m_bmp_red_arrow_left;

	if(m_bmp_bubble_arrow_bottom)
		delete m_bmp_bubble_arrow_bottom;
	if(m_bmp_bubble_arrow_left)
		delete m_bmp_bubble_arrow_left;
	if(m_bmp_bubble_arrow_right)
		delete m_bmp_bubble_arrow_right;
	if(m_bmp_bubble_arrow_top)
		delete m_bmp_bubble_arrow_top;

	if(m_bmp_bubble_corner_ne)
		delete m_bmp_bubble_corner_ne;
	if(m_bmp_bubble_corner_nw)
		delete m_bmp_bubble_corner_nw;
	if(m_bmp_bubble_corner_se)
		delete m_bmp_bubble_corner_se;
	if(m_bmp_bubble_corner_sw)
		delete m_bmp_bubble_corner_sw;

	if(m_bmp_bubble_edge_bottom)
		delete m_bmp_bubble_edge_bottom;
	if(m_bmp_bubble_edge_left)
		delete m_bmp_bubble_edge_left;
	if(m_bmp_bubble_edge_right)
		delete m_bmp_bubble_edge_right;
	if(m_bmp_bubble_edge_top)
		delete m_bmp_bubble_edge_top;

	m_tile = null;
}

//
// Public method/s
//

boolean raGamePanel.SetTile(wxBitmap *tile)
{
	assert(tile);

	// Cleanup if m_tile is already created
	if(m_tile)
		delete m_tile;
	m_tile = null;

	// Create a new tile bitmap
	m_tile = new wxBitmap(*tile);
	if(!m_tile)
	{
		wxLogError(String.Format(("Attempt create bitmap failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	// As the tile has now changed, redraw the back buffer
	if(!RedrawBack())
	{
		wxLogError(String.Format(("Attempt to redraw back buffer failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	return true;
}

boolean raGamePanel.SetInfoPanel(raInfo *info_panel)
{
	assert(info_panel);
	m_info = info_panel;
	return true;
}
boolean raGamePanel.NewGame(int dealer, boolean immediate)
{
	if(!ResetGame())
	{
		wxLogError(String.Format(("ResetGame failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	if(dealer == gmPLAYER_INVALID)
		dealer = 0;

	m_engine.SetDealer(dealer);

	m_info.ResetDetails(true);
	if(immediate)
	{
		if(!NewDeal())
		{
			wxLogError(String.Format(("NewDeal failed. %s:%d"), (__FILE__), __LINE__));
			return false;
		}
	}
	else
	{
		m_info.SetInstruction(("New Game started. Please click on the button below to deal cards."),
			raINFO_CMD_NEW_DEAL);
	}
	return true;
}
boolean raGamePanel.NewDeal()
{
	int dealer = gmPLAYER_INVALID;
//	gmRandState rs;
//
//    rs.state_array[0]=0x9076251A;
//    rs.state_array[1]=0xF4A30A9F;
//    rs.state_array[2]=0xACE20BA7;
//    rs.state_array[3]=0x62C0533A;
//    rs.state_array[4]=0xDD296836;
//    rs.state_array[5]=0xB3351062;
//    rs.state_array[6]=0xA231279E;
//    rs.state_array[7]=0x9F543675;
//    rs.state_array[8]=0xCDDF4354;
//    rs.state_array[9]=0x422D0262;
//    rs.state_array[10]=0x41BD47A6;
//    rs.state_array[11]=0x1DAE363B;
//    rs.state_array[12]=0x6F4558F2;
//    rs.state_array[13]=0x2D38925A;
//    rs.state_array[14]=0xFF2EE03B;
//    rs.state_array[15]=0xE9EDA6B6;
//    rs.state_array[16]=0xB8341066;
//    rs.state_array[17]=0x53B16599;
//    rs.state_array[18]=0xB17473E9;
//    rs.state_array[19]=0x485452D0;
//
//    rs.idx=1;
//    gmRand.SetState(&rs);
//
//    dealer = 2;



//#ifdef raREAD_SEED_FROM_FILE
	//long seed_read;
    int i = 0;
    gmRandState rand_state;
    String seed_text;
    String in_txt;
    long in_long;
    boolean status = true;

	if(.wxFileExists(raTEST_DATA_FILE))
	{
		wxFFileInputStream in(raTEST_DATA_FILE);
		wxFileConfig fcfg(in);

        memset(&rand_state, 0, sizeof(gmRandState));
        rand_state.idx = -2;
        //seed_read = -1;

        // Read the idx value
        if(fcfg.Exists(raTEXT_IDX))
        {
            wxLogDebug(String.Format(("Reading idx from %s. %s:%d"),
				raTEST_DATA_FILE, (__FILE__), __LINE__));
			if(!fcfg.Read(raTEXT_IDX, &(rand_state.idx)))
			{
				wxLogError(String.Format(
					("Read failed for %s. %s:%d"), raTEXT_IDX, (__FILE__), __LINE__));
                status = false;
			}
        }
        else
        {
            wxLogError(String.Format(
                ("Could not fine %s in %s . %s:%d"), raTEXT_IDX, raTEST_DATA_FILE, (__FILE__), __LINE__));
            status = false;
        }

        // Read the state array
        if(status == true)
        {
            for(i = 0; i < N32; i++)
            {
                seed_text = String.Format(("%s%02d"), raTEXT_SEED, i);
                if(fcfg.Exists(seed_text))
                {
                    wxLogDebug(String.Format(
                        ("Reading %s from %s. %s:%d"),
                        seed_text.c_str(), raTEST_DATA_FILE, (__FILE__), __LINE__));
                    if(!fcfg.Read(seed_text, &in_txt))
                    {
                        wxLogError(String.Format(
                            ("Read failed. %s:%d"), (__FILE__), __LINE__));
                        status = false;
                        break;

                    }
                    wxLogDebug(String.Format(("String data seed%d=%s"), i, in_txt.c_str()));
                    in_txt.ToULong(&in_long, 16);
                    rand_state.state_array[i] = (int)in_long;
                    wxLogDebug(String.Format(("int seed%02d=%08X"), i, rand_state.state_array[i]));
                }
                else
                {
                    wxLogError(String.Format(
                        ("Could not find %s in %s. %s:%d"),
                        seed_text.c_str(), raTEST_DATA_FILE, (__FILE__), __LINE__));
                    status = false;
                    break;
                }
            }
        }

        if(status == true)
        {
            wxLogDebug(("Complete state read. Setting state."));
            gmRand.SetState(&rand_state);
        }
	}
//#endif

//#ifdef raREAD_DEALER_FROM_FILE
	long dealer_read;
	if(.wxFileExists(raTEST_DATA_FILE))
	{
		wxFFileInputStream in(raTEST_DATA_FILE);
		wxFileConfig fcfg(in);
		if(fcfg.Exists(raTEXT_DEALER))
		{
			wxLogDebug(String.Format(
				("Reading dealer from %s. %s:%d"),
				raTEST_DATA_FILE, (__FILE__), __LINE__));

			dealer_read = -1;
			if(!fcfg.Read(raTEXT_DEALER, &dealer_read))
			{
				wxLogError(String.Format(
					("Read failed. %s:%d"), (__FILE__), __LINE__));

			}
			else
			{
				dealer = (int)dealer_read;
				assert((dealer >= 0) && (dealer < gmTOTAL_PLAYERS));
			}
		}
		else
		{
			wxLogError(String.Format(
				("Could not find dealer in %s. %s:%d"),
				raTEST_DATA_FILE, (__FILE__), __LINE__));
		}
	}
//#endif

	wxLogMessage(gmRand.PrintState());


	// Save the dealer information lest it gets reset
	// while resetting the rule engine
	if(dealer == gmPLAYER_INVALID)
	{
		dealer = m_engine.GetDealer();
	}
	if(!ResetDeal())
	{
		wxLogError(String.Format(("ResetDeal failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	assert((dealer > gmPLAYER_INVALID) && (dealer < gmTOTAL_PLAYERS));
	m_engine.SetDealer(dealer);
	wxLogMessage(String.Format(("Dealer - %s"), gmUtil.m_long_locs[dealer].c_str()));

	while(Continue());
	return true;
}

boolean raGamePanel.SetClockwise(boolean flag)
{
	gmEngineData data;
	int i;

	// TODO : Add appropriate checks as to when this can be done.

	// Set the direction of game play for the rule engine
	m_engine.GetData(&data);
	if(flag)
		data.rules.rot_addn = 1;
	else
		data.rules.rot_addn = 3;
	m_engine.SetData(&data, false);

	// TODO : Set the direction of game play for the AI players
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		m_players[i].SetClockwise(flag);
	}

	m_clockwise = flag;
	return true;
}
boolean raGamePanel.GetClockwise()
{
	return m_clockwise;
}
boolean raGamePanel.ReloadFromConfig()
{
	raConfData conf_data;

	raConfig.GetInstance().GetData(&conf_data);

	m_pref_card_back = conf_data.prefs_data.card_back;
	m_play_card_on = conf_data.prefs_data.play_card_on;
	m_auto_play =conf_data.prefs_data.auto_play_single;
	m_show_bidbubbles = conf_data.prefs_data.show_bid_bubbles;

	UpdateDrawAndRefresh();

	return true;
}
boolean raGamePanel.ShowAuction()
{
	if(m_engine.GetStatus() >= gmSTATUS_BID1)
	{
		wxMessageBox(m_bid_history, ("Details of the auction"));
	}
	else
	{
		wxMessageBox(("Data not available"));
	}
	return true;
}

boolean raGamePanel.ShowLastTrick()
{
	String out;
	int i;
	gmEngineData data;
	gmTrick *trick;

	m_engine.GetData(&data);
	if((data.status >= gmSTATUS_TRICKS) && (data.trick_round > 0))
	{
		out.Append(("Round - "));
		out.Append(String.Format(("%d"), data.trick_round));
		out.Append(("\n\n"));
		trick = &data.tricks[data.trick_round - 1];
		for(i = 0; i < gmTOTAL_PLAYERS; i++)
		{
			out.Append(gmUtil.m_long_locs[i]);
			out.Append(("\t- "));
			assert(trick.cards[i] != gmCARD_INVALID);
			out.Append(gmUtil.m_suits[gmGetSuit(trick.cards[i])]);
			out.Append(gmUtil.m_values[gmGetValue(trick.cards[i])]);
			if(trick.lead_loc == i)
			{
				out.Append((" ("));
				out.Append(("Lead"));
				out.Append((")"));
			}
			if(trick.winner == i)
			{
				out.Append((" ("));
				out.Append(("Winner"));
				out.Append((")"));
			}
			out.Append(("\n"));
		}
		wxMessageBox(out, ("Last Trick"));
	}
	else
	{
		wxMessageBox(("Data not available"));
	}
	return true;
}

//
// Private method/s
//
void raGamePanel.OnSize(wxSizeEvent& event)
{
	wxPaintEvent new_event;

	// Set the location of the bid panel
	//m_bid.SetSize(
	//	(this.GetClientSize().GetWidth() - m_bid.GetSize().GetWidth()) / 2,
	//	(this.GetClientSize().GetHeight() - m_bid.GetSize().GetHeight()) / 2,
	//	m_bid.GetSize().GetWidth(),
	//	m_bid.GetSize().GetHeight()
	//	);
	m_bid.Move(
		(this.GetClientSize().GetWidth() - m_bid.GetSize().GetWidth()) / 2,
		(this.GetClientSize().GetHeight() - m_bid.GetSize().GetHeight()) / 2
		);

	// Get all the elements assosiated with this panel resized
	if(!Size())
		wxLogError(String.Format(("Attempt to size the panel failed. %s:%d"), (__FILE__), __LINE__));

	if(!UpdateDrawAndRefresh(false))
	{
		wxLogError(String.Format(("Call to UpdateDrawAndRefresh() failed. %s:%d"), (__FILE__), __LINE__));
	}

	event.Skip();
}
boolean raGamePanel.RedrawBack(raBackDrawInfo *info)
{
	int i, j, x, y, wt, ht, wb, hb;
	wxMemoryDC mdc;
	String loc_text;
	int text_width, text_height;
	int horz_pnl_relief = 0, vert_pnl_relief = 0;

	//wxBitmap bmp_green_arrow_bottom(green_arrow_bottom_xpm);
	//wxBitmap bmp_green_arrow_top(green_arrow_top_xpm);
	//wxBitmap bmp_green_arrow_right(green_arrow_right_xpm);
	//wxBitmap bmp_green_arrow_left(green_arrow_left_xpm);

	// Obtain the dimensions of the back buffer
	wb = 0;
	hb = 0;
	this.GetClientSize(&wb, &hb);

	// If there is a valid tile, draw the tile all over the place
	if(m_tile)
	{
		// Obtain the dimensions of the tile
		wt = m_tile.GetWidth();
		ht = m_tile.GetHeight();

		// Obtain the dimensions of the back buffer
		wb = 0;
		hb = 0;
		this.GetClientSize(&wb, &hb);

		// Calculate the number of times the tile
		// has to blitted to the back buffer vertically
		// and horizontally
		x = (wb / wt) + !!(wb % wt);
		y = (hb / ht) + !!(hb % ht);

		// Blit repeatedly
		mdc.SelectObject(*m_tile);

		for(i = 0; i < x; i++)
		{
			for(j = 0; j < y;  j++)
			{
				if(!BlitToBack(i * wt, j * ht, wt, ht, &mdc, 0, 0))
				{
					wxLogError(String.Format(("Blit to back buffer failed. %s:%d"), (__FILE__), __LINE__));
					return false;
				}
			}
		}

		// Cleanup the object selection
		mdc.SelectObject(wxnullBitmap);
	}

	// Invalidate the existing hand positions and dimensions
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
		m_hand_rects[i] = wxRect(0, 0, 0, 0);

	mdc.SetFont(m_font_bold);
	//
	// Draw the location text at the bottom
	//

	i = (m_hand_rot * (gmTOTAL_PLAYERS - 1)) % 4;

	loc_text = gmUtil.m_long_locs[i];
	mdc.GetTextExtent(loc_text, &text_width, &text_height);
	x = (wb - text_width) / 2;
	y = hb - text_height;
	DrawTextOnBack(loc_text, wxPoint(x + 1, y + 1), *wxBLACK, m_font_bold);
	DrawTextOnBack(loc_text, wxPoint(x, y), *wxWHITE, m_font_bold);

	vert_pnl_relief = std.max(vert_pnl_relief, text_height);

	//
	// Draw the location text at the left
	//

	i = ((m_hand_rot * (gmTOTAL_PLAYERS - 1)) + 1) % 4;

	loc_text = gmUtil.m_long_locs[i];
	mdc.GetTextExtent(loc_text, &text_width, &text_height);
	x = 0;
	y = (hb - text_height) / 2;
	DrawTextOnBack(loc_text, wxPoint(x + 1, y + 1), *wxBLACK, m_font_bold);
	DrawTextOnBack(loc_text, wxPoint(x, y), *wxWHITE, m_font_bold);

	horz_pnl_relief = std.max(horz_pnl_relief, text_width);

	//
	// Draw the text for the location at top
	//

	i = ((m_hand_rot * (gmTOTAL_PLAYERS - 1)) + 2) % 4;

	loc_text = gmUtil.m_long_locs[i];
	mdc.GetTextExtent(loc_text, &text_width, &text_height);
	x = (wb - text_width) / 2;
	y = 0;
	DrawTextOnBack(loc_text, wxPoint(x + 1, y + 1), *wxBLACK, m_font_bold);
	DrawTextOnBack(loc_text, wxPoint(x, y), *wxWHITE, m_font_bold);

	vert_pnl_relief = std.max(vert_pnl_relief, text_height);

	//
	// Draw the location text at the right
	//

	i = ((m_hand_rot * (gmTOTAL_PLAYERS - 1)) + 3) % 4;

	loc_text = gmUtil.m_long_locs[i];
	mdc.GetTextExtent(loc_text, &text_width, &text_height);
	x = wb - text_width;
	y = (hb - text_height) / 2;
	DrawTextOnBack(loc_text, wxPoint(x + 1, y + 1), *wxBLACK, m_font_bold);
	DrawTextOnBack(loc_text, wxPoint(x, y), *wxWHITE, m_font_bold);

	horz_pnl_relief = std.max(horz_pnl_relief, text_width);

	//
	// Draw the hand to be shown at the bottom
	//

	i = (m_hand_rot * (gmTOTAL_PLAYERS - 1)) % 4;

	// Calculate the starting position to draw
	x = (wb - (GG_CARD_WIDTH + ((m_hands[i].count - 1) * raCARD_HORZ_RELIEF))) / 2;
	y = hb - std.max(raCARD_PANEL_RELIEF, vert_pnl_relief) - GG_CARD_HEIGHT;

	// Draw the hand at the calculated location
	DrawHand(i, x, y);

	//
	// Draw the hand to be shown at the left
	//

	i = ((m_hand_rot * (gmTOTAL_PLAYERS - 1)) + 1) % 4;

	// Calculate the starting position to draw
	if(m_orientation == raGAME_ORIENT_ALL_HORZ)
	{
		x = std.max(raCARD_PANEL_RELIEF, horz_pnl_relief);
		y = (hb - GG_CARD_HEIGHT) / 2;

		// Draw the hand at the calculated location
		DrawHand(i, x, y);
	}
	else
	{
		x = std.max(raCARD_PANEL_RELIEF, horz_pnl_relief);
		y = (hb - (GG_CARD_HEIGHT + ((m_hands[i].count - 1) * raCARD_VERT_RELIEF))) / 2;

		// Draw the hand at the calculated location
		DrawHand(i, x, y, raHAND_VERTICAL);
	}

	//
	// Draw the hand to be shown at the top
	//

	i = ((m_hand_rot * (gmTOTAL_PLAYERS - 1)) + 2) % 4;

	// Calculate the starting position to draw
	x = (wb - (GG_CARD_WIDTH + ((m_hands[i].count - 1) * raCARD_HORZ_RELIEF))) / 2;
	y = std.max(raCARD_PANEL_RELIEF, vert_pnl_relief);

	// Draw the hand at the calculated location
	DrawHand(i, x, y);

	//
	// Draw the hand to be shown at the right
	//

	i = ((m_hand_rot * (gmTOTAL_PLAYERS - 1)) + 3) % 4;

	// Calculate the starting position to draw
	if(m_orientation == raGAME_ORIENT_ALL_HORZ)
	{
		x = (wb - std.max(raCARD_PANEL_RELIEF, horz_pnl_relief) -
			(GG_CARD_WIDTH + ((m_hands[i].count - 1) * raCARD_HORZ_RELIEF)));
		y = (hb - GG_CARD_HEIGHT) / 2;
		// Draw the hand at the calculated location
		DrawHand(i, x, y);

	}
	else
	{
		x = (wb - std.max(raCARD_PANEL_RELIEF, horz_pnl_relief) - GG_CARD_WIDTH);
		y = (hb - (GG_CARD_HEIGHT + ((m_hands[i].count - 1) * raCARD_VERT_RELIEF))) / 2;
		// Draw the hand at the calculated location
		DrawHand(i, x, y, raHAND_VERTICAL);
	}

	// Show green arrow for the next card to be played
	if(m_trick.count < gmTOTAL_PLAYERS)
	{
		i = m_engine.GetTrickNextToPlay();
		if(i != gmPLAYER_INVALID)
		{
			switch(i)
			{
			case 0:
				mdc.SelectObject(*m_bmp_green_arrow_top);
				x = (wb - raARROW_WIDTH) / 2;
				y = m_hand_rects[0].GetTop() - raARROW_WIDTH - raGAME_ARROW_RELIEF;
				//x = m_hand_rects[0].GetRight() + raGAME_ARROW_RELIEF;
				//y = (hb - raARROW_WIDTH) / 2;
				break;
			case 1:
				mdc.SelectObject(*m_bmp_green_arrow_right);
				x = m_hand_rects[1].GetRight() + raGAME_ARROW_RELIEF;
				y = (hb - raARROW_WIDTH) / 2;
				//x = (wb - raARROW_WIDTH) / 2;
				//y = m_hand_rects[1].GetBottom() + raGAME_ARROW_RELIEF;
				break;
			case 2:
				mdc.SelectObject(*m_bmp_green_arrow_bottom);
				x = (wb - raARROW_WIDTH) / 2;
				y = m_hand_rects[2].GetBottom() + raGAME_ARROW_RELIEF;
				//x = m_hand_rects[2].GetLeft() - raARROW_WIDTH - raGAME_ARROW_RELIEF;
				//y = (hb - raARROW_WIDTH) / 2;
				break;
			case 3:
				mdc.SelectObject(*m_bmp_green_arrow_left);
				x = m_hand_rects[3].GetLeft() - raARROW_WIDTH - raGAME_ARROW_RELIEF;
				y = (hb - raARROW_WIDTH) / 2;
				//x = (wb - raARROW_WIDTH) / 2;
				//y = m_hand_rects[3].GetTop() - raARROW_WIDTH - raGAME_ARROW_RELIEF;
				break;
			}
			BlitToBack(x, y, raARROW_WIDTH, raARROW_WIDTH, &mdc, 0, 0, wxCOPY, true);
		}
	}


	// Draw the cards in the current trick
	DrawTrick();

	// Draw the trump card
	DrawTrump();

	// Draw the text for the trump
	loc_text = ("Trump");
	mdc.GetTextExtent(loc_text, &text_width, &text_height);
	mdc.GetTextExtent(loc_text, &text_width, &text_height);
	x = raCARD_PANEL_RELIEF + ((GG_CARD_WIDTH - text_width) / 2);
	y = 0;
	DrawTextOnBack(loc_text, wxPoint(x + 1, y + 1), *wxBLACK, m_font_bold);
	DrawTextOnBack(loc_text, wxPoint(x, y), *wxWHITE, m_font_bold);

	if(m_show_bidbubbles)
	{
		// If required, draw the bubble which has the bid
		if(info)
		{
			if(info.draw_bid)
			{
				wxMemoryDC temp_dc1, temp_dc2;
				String bubb_text;
				int u, v;

				if(info.bid == gmBID_PASS)
					bubb_text = String.Format(("%s passes the bid"), gmUtil.m_long_locs[info.bid_loc].c_str());
				else
					bubb_text = String.Format(("%s bids %d"), gmUtil.m_long_locs[info.bid_loc].c_str(), info.bid);

				u = 0;
				v = 0;

				mdc.SetFont(m_font_bold);

				mdc.GetTextExtent(bubb_text, &u, &v);

				// TODO : Remove hard coding of the relief

				x = std.max((u + 8), raBUBB_MIN_WIDTH);
				y = std.max((v + 8), raBUBB_MIN_HEIGHT);

				// Increment x and y to the equal or higher multiple of raBUBB_UNIT_MIN
				x = ((x / raBUBB_UNIT_MIN) + (!!(x % raBUBB_UNIT_MIN))) * raBUBB_UNIT_MIN;
				y = ((y / raBUBB_UNIT_MIN) + (!!(y % raBUBB_UNIT_MIN))) * raBUBB_UNIT_MIN;

				wxBitmap bmp_bubb_rect(x, y);

				mdc.SelectObject(bmp_bubb_rect);
				mdc.SetTextForeground(*wxBLACK);

				// Fill with the masking colour
				mdc.SetBrush(wxBrush(*wxRED));
				mdc.SetPen(wxPen(*wxRED, 0));
				mdc.DrawRectangle(wxRect(0, 0, x, y));

				// Fill with the background colour for the bubble
				mdc.SetBrush(wxBrush(raCLR_BUBB_FILL));
				mdc.SetPen(wxPen(raCLR_BUBB_FILL, 0));
				mdc.DrawRectangle(wxRect(raBUBB_UNIT_MIN, raBUBB_UNIT_MIN,
					x - (2 * raBUBB_UNIT_MIN), y - (2 * raBUBB_UNIT_MIN)));

				// Draw corners
				temp_dc1.SelectObject(*m_bmp_bubble_corner_nw);
				mdc.Blit(0, 0, raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, &temp_dc1, 0, 0, wxCOPY, true);
				temp_dc1.SelectObject(*m_bmp_bubble_corner_ne);
				mdc.Blit(x - raBUBB_UNIT_MIN, 0, raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, &temp_dc1, 0, 0, wxCOPY, true);
				temp_dc1.SelectObject(*m_bmp_bubble_corner_sw);
				mdc.Blit(0, y - raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, &temp_dc1, 0, 0, wxCOPY, true);
				temp_dc1.SelectObject(*m_bmp_bubble_corner_se);
				mdc.Blit(x - raBUBB_UNIT_MIN, y - raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, &temp_dc1, 0, 0, wxCOPY, true);

				// Draw the top and bottom edges
				temp_dc1.SelectObject(*m_bmp_bubble_edge_top);
				temp_dc2.SelectObject(*m_bmp_bubble_edge_bottom);
				for(i = 1; i <= ((x / raBUBB_UNIT_MIN) - 2); i++)
				{
					mdc.Blit(i * raBUBB_UNIT_MIN, 0, raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, &temp_dc1, 0, 0, wxCOPY, true);
					mdc.Blit(i * raBUBB_UNIT_MIN, y - raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, &temp_dc2, 0, 0, wxCOPY, true);
				}

				// Draw the left and right edges
				temp_dc1.SelectObject(*m_bmp_bubble_edge_left);
				temp_dc2.SelectObject(*m_bmp_bubble_edge_right);
				for(i = 1; i <= ((y / raBUBB_UNIT_MIN) - 2); i++)
				{
					mdc.Blit(0, i * raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, &temp_dc1, 0, 0, wxCOPY, true);
					mdc.Blit(x - raBUBB_UNIT_MIN, i * raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, &temp_dc2, 0, 0, wxCOPY, true);
				}

				//mdc.Blit(10, 0, raBUBB_UNIT_MIN, raBUBB_UNIT_MIN, &temp_dc1, 0, 0, wxCOPY, true);

				mdc.SelectObject(wxnullBitmap);

				// Create a mask for the bitmap
				// (There is no need to delete the mask
				// wxWidgets will take care of it. Koppu!)
				wxMask *mask_bubb_rect = new wxMask(bmp_bubb_rect, *wxRED);
				bmp_bubb_rect.SetMask(mask_bubb_rect);

				// Draw the text
				mdc.SelectObject(bmp_bubb_rect);
				//wxLogDebug(String.Format("x - %d u - %d", x, u));
				mdc.DrawText(bubb_text, wxPoint((x - u) / 2, (y - v) / 2));
				mdc.SelectObject(wxnullBitmap);

				// Create a new bitmap which will hold the bubble
				// rectangle as well as the arrow

				// u and v will contain the dimensions of the full bubble
				// i and j will contain the position of the bubble

				wxBitmap *bmp_bubb_full = null;
				switch(info.bid_loc)
				{
				case 0:
					u = x;
					v = y + raBUBB_ARROW_PROTUN;
					bmp_bubb_full = new wxBitmap(u, v);
					mdc.SelectObject(*bmp_bubb_full);
					mdc.SetBrush(wxBrush(*wxRED));
					mdc.SetPen(wxPen(*wxRED, 0));
					mdc.DrawRectangle(wxRect(0, 0, u, v));
					temp_dc1.SelectObject(bmp_bubb_rect);
					mdc.Blit(0, 0, x, y, &temp_dc1, 0, 0, wxCOPY, true);
					temp_dc1.SelectObject(*m_bmp_bubble_arrow_bottom);
					mdc.Blit((x - raBUBB_ARROW_WIDTH) / 2, y - raBUBB_ARROW_OVERLAP, x, y, &temp_dc1, 0, 0, wxCOPY, true);

					i = (wb - u) / 2;
					j = m_hand_rects[0].GetTop() - v - raGAME_ARROW_RELIEF;
					break;
				case 1:
					u = x + raBUBB_ARROW_PROTUN;
					v = y;
					bmp_bubb_full = new wxBitmap(u, v);
					mdc.SelectObject(*bmp_bubb_full);
					mdc.SetBrush(wxBrush(*wxRED));
					mdc.SetPen(wxPen(*wxRED, 0));
					mdc.DrawRectangle(wxRect(0, 0, u, v));
					temp_dc1.SelectObject(bmp_bubb_rect);
					mdc.Blit(raBUBB_ARROW_PROTUN, 0, x, y, &temp_dc1, 0, 0, wxCOPY, true);
					temp_dc1.SelectObject(*m_bmp_bubble_arrow_left);
					mdc.Blit(0, (y - raBUBB_ARROW_WIDTH) / 2, x, y, &temp_dc1, 0, 0, wxCOPY, true);

					i = m_hand_rects[1].GetRight() + raGAME_ARROW_RELIEF;
					j = (hb - v) / 2;
					break;
				case 2:
					u = x;
					v = y + raBUBB_ARROW_PROTUN;
					bmp_bubb_full = new wxBitmap(u, v);
					mdc.SelectObject(*bmp_bubb_full);
					mdc.SetBrush(wxBrush(*wxRED));
					mdc.SetPen(wxPen(*wxRED, 0));
					mdc.DrawRectangle(wxRect(0, 0, u, v));
					temp_dc1.SelectObject(bmp_bubb_rect);
					mdc.Blit(0, raBUBB_ARROW_PROTUN, x, y, &temp_dc1, 0, 0, wxCOPY, true);
					temp_dc1.SelectObject(*m_bmp_bubble_arrow_top);
					mdc.Blit((x - raBUBB_ARROW_WIDTH) / 2, 0, x, y, &temp_dc1, 0, 0, wxCOPY, true);

					i = (wb - u) / 2;
					j = m_hand_rects[2].GetBottom() + raGAME_ARROW_RELIEF;
					break;
				case 3:
					u = x + raBUBB_ARROW_PROTUN;
					v = y;
					bmp_bubb_full = new wxBitmap(u, v);
					mdc.SelectObject(*bmp_bubb_full);
					mdc.SetBrush(wxBrush(*wxRED));
					mdc.SetPen(wxPen(*wxRED, 0));
					mdc.DrawRectangle(wxRect(0, 0, u, v));
					temp_dc1.SelectObject(bmp_bubb_rect);
					mdc.Blit(0, 0, x, y, &temp_dc1, 0, 0, wxCOPY, true);
					temp_dc1.SelectObject(*m_bmp_bubble_arrow_right);
					mdc.Blit(x - raBUBB_ARROW_OVERLAP, (y - raBUBB_ARROW_WIDTH) / 2, x, y, &temp_dc1, 0, 0, wxCOPY, true);

					i = m_hand_rects[3].GetLeft() - u - raGAME_ARROW_RELIEF;
					j = (hb - v) / 2;
					break;
				}
				mdc.SelectObject(wxnullBitmap);

				wxMask *mask_bubb_full = new wxMask(*bmp_bubb_full, *wxRED);
				bmp_bubb_full.SetMask(mask_bubb_full);

				mdc.SelectObject(*bmp_bubb_full);
				BlitToBack(i, j, u, v, &mdc, 0, 0, wxCOPY, true);
				if(bmp_bubb_full)
					delete bmp_bubb_full;

				//mdc.SelectObject(bmp_bubb_rect);
				//BlitToBack(0, 0, x, y, &mdc, 0, 0, wxCOPY, true);

			}
		}
	}

	return true;
}

boolean raGamePanel.DrawHand(int loc, int x, int y, int orientation)
{
	int j;
	wxMemoryDC cfdc, cbdc;
	int player_type;
	assert((orientation == raHAND_HORIZONTAL) ||
		(orientation == raHAND_VERTICAL));

	player_type = m_players[loc].GetType();

//#ifdef raGAME_HIDE_AI_HANDS
	if(player_type == raPLAYER_TYPE_AI)
		cbdc.SelectObject(*m_card_backs[m_pref_card_back]);
//#endif

	if(orientation == raHAND_HORIZONTAL)
	{
		for(j = 0; j < m_hands[loc].count; j++)
		{
//#ifdef raGAME_HIDE_AI_HANDS
			if(player_type == raPLAYER_TYPE_AI)
			{
				BlitToBack(x + (raCARD_HORZ_RELIEF * j), y, GG_CARD_WIDTH, GG_CARD_HEIGHT, &cbdc, 0, 0, wxCOPY, true);
			}
			else
			{
//#endif
				cfdc.SelectObject(*m_card_faces[m_hands[loc].card_indexes[j]]);
				BlitToBack(x + (raCARD_HORZ_RELIEF * j), y, GG_CARD_WIDTH, GG_CARD_HEIGHT, &cfdc, 0, 0, wxCOPY, true);
//#ifdef raGAME_HIDE_AI_HANDS
			}
//#endif

			// Update the position and dimensions of cards and hands
			m_hand_card_rects[loc][j] = wxRect(x + (raCARD_HORZ_RELIEF * j), y, GG_CARD_WIDTH, GG_CARD_HEIGHT);

			// TODO : Correction for overlapping
			m_hand_rects[loc].Union(m_hand_card_rects[loc][j]);
		}
	}
	else
	{
		for(j = 0; j < m_hands[loc].count; j++)
		{
//#ifdef raGAME_HIDE_AI_HANDS
			if(player_type == raPLAYER_TYPE_AI)
			{
				BlitToBack(x, y + (raCARD_VERT_RELIEF * j), GG_CARD_WIDTH, GG_CARD_HEIGHT, &cbdc, 0, 0, wxCOPY, true);
			}
			else
			{
//#endif
				cfdc.SelectObject(*m_card_faces[m_hands[loc].card_indexes[j]]);
				BlitToBack(x, y + (raCARD_VERT_RELIEF * j), GG_CARD_WIDTH, GG_CARD_HEIGHT, &cfdc, 0, 0, wxCOPY, true);
//#ifdef raGAME_HIDE_AI_HANDS
			}
//#endif

			// Update the position and dimensions of cards and hands
			if(orientation == raHAND_HORIZONTAL)
				m_hand_card_rects[loc][j] = wxRect(x + (raCARD_HORZ_RELIEF * j), y, GG_CARD_WIDTH, GG_CARD_HEIGHT);
			else
				m_hand_card_rects[loc][j] = wxRect(x, y + (raCARD_VERT_RELIEF * j), GG_CARD_WIDTH, GG_CARD_HEIGHT);

			// TODO : Correction for overlapping
			m_hand_rects[loc].Union(m_hand_card_rects[loc][j]);
		}
	}

	return true;
}

boolean raGamePanel.DrawTrick()
{
	int i;
	int loc;
	wxMemoryDC mdc;
	int rot_addn;

	//wxBitmap bmp_red_arrow_bottom(red_arrow_bottom_xpm);
	//wxBitmap bmp_red_arrow_top(red_arrow_top_xpm);
	//wxBitmap bmp_red_arrow_right(red_arrow_right_xpm);
	//wxBitmap bmp_red_arrow_left(red_arrow_left_xpm);

	if(m_clockwise)
		rot_addn = 1;
	else
		rot_addn = 3;

	for(i = 0; i < gmTOTAL_PLAYERS; i++)
		m_trick_card_rects[i] = wxRect(0, 0, 0, 0);

	for(i = 0; i < m_trick.count; i++)
	{
		//loc = (m_trick.lead_loc + i) % gmTOTAL_PLAYERS;
		loc = (m_trick.lead_loc + (i * rot_addn)) % gmTOTAL_PLAYERS;
		mdc.SelectObject(*m_card_faces[m_trick.cards[loc]]);
		switch(loc)
		{
		case 0:
			// Draw at the bottom
			m_trick_card_rects[0] = wxRect(
				(this.GetClientSize().GetWidth() - GG_CARD_WIDTH) / 2,
				((this.GetClientSize().GetHeight() - GG_CARD_HEIGHT) / 2) + (GG_CARD_HEIGHT / 4),
				GG_CARD_WIDTH, GG_CARD_HEIGHT);
			BlitToBack(m_trick_card_rects[0].x, m_trick_card_rects[0].y,
				GG_CARD_WIDTH, GG_CARD_HEIGHT, &mdc, 0, 0, wxCOPY, true);
			break;
		case 1:
			m_trick_card_rects[1] = wxRect(
				((this.GetClientSize().GetWidth() - GG_CARD_WIDTH) / 2) - (GG_CARD_WIDTH / 4),
				(this.GetClientSize().GetHeight() - GG_CARD_HEIGHT) / 2,
				GG_CARD_WIDTH, GG_CARD_HEIGHT);
			BlitToBack(m_trick_card_rects[1].x, m_trick_card_rects[1].y,
				GG_CARD_WIDTH, GG_CARD_HEIGHT, &mdc, 0, 0, wxCOPY, true);
			break;
		case 2:
			m_trick_card_rects[2] = wxRect(
				(this.GetClientSize().GetWidth() - GG_CARD_WIDTH) / 2,
				((this.GetClientSize().GetHeight() - GG_CARD_HEIGHT) / 2) - (GG_CARD_HEIGHT / 4),
				GG_CARD_WIDTH, GG_CARD_HEIGHT);
			BlitToBack(m_trick_card_rects[2].x, m_trick_card_rects[2].y,
				GG_CARD_WIDTH, GG_CARD_HEIGHT, &mdc, 0, 0, wxCOPY, true);
			break;
		case 3:
			m_trick_card_rects[3] = wxRect(
				((this.GetClientSize().GetWidth() - GG_CARD_WIDTH) / 2) + (GG_CARD_WIDTH / 4),
				(this.GetClientSize().GetHeight() - GG_CARD_HEIGHT) / 2,
				GG_CARD_WIDTH, GG_CARD_HEIGHT);
			BlitToBack(m_trick_card_rects[3].x, m_trick_card_rects[3].y,
				GG_CARD_WIDTH, GG_CARD_HEIGHT, &mdc, 0, 0, wxCOPY, true);
			break;
		}
	}

	// If the trick has ended, graphically indicate the winner
	// using the red arrow
	if(m_trick.count == gmTOTAL_PLAYERS)
	{
		int x = 0, y = 0;

		switch(m_trick.winner)
		{
		case 0:
			mdc.SelectObject(*m_bmp_red_arrow_bottom);
			x = (this.GetClientSize().GetWidth() - raARROW_WIDTH) / 2;
			y = m_trick_card_rects[0].GetBottom() + raGAME_ARROW_RELIEF;
			break;
		case 1:
			mdc.SelectObject(*m_bmp_red_arrow_left);
			x = m_trick_card_rects[1].GetLeft() - raARROW_WIDTH - raGAME_ARROW_RELIEF;
			y = (this.GetClientSize().GetHeight() - raARROW_WIDTH) / 2;
			break;
		case 2:
			mdc.SelectObject(*m_bmp_red_arrow_top);
			x = (this.GetClientSize().GetWidth() - raARROW_WIDTH) / 2;
			y = m_trick_card_rects[2].GetTop() - raARROW_WIDTH - raGAME_ARROW_RELIEF;
			break;
		case 3:
			mdc.SelectObject(*m_bmp_red_arrow_right);
			x = m_trick_card_rects[3].GetRight() + raGAME_ARROW_RELIEF;
			y = (this.GetClientSize().GetHeight() - raARROW_WIDTH) / 2;
			break;
		default:
			break;
		}
		BlitToBack(x, y, raARROW_WIDTH, raARROW_WIDTH,
			&mdc, 0, 0, wxCOPY, true);
	}

	return true;
}

boolean raGamePanel.DrawTrump()
{
	int trump_card;
	int max_bidder;
	int player_type;
	wxMemoryDC mdc;

	if(!m_engine.GetMaxBid(null, &max_bidder))
	{
		wxLogError(String.Format(("GetMaxBid failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	assert((max_bidder >= gmPLAYER_INVALID) && (max_bidder < gmTOTAL_PLAYERS));
	if(max_bidder == gmPLAYER_INVALID)
		return true;

	player_type = m_players[max_bidder].GetType();
	assert((player_type >= raPLAYER_TYPE_INVALID) && (player_type <= raPLAYER_TYPE_AI));

	trump_card = m_engine.GetTrumpCard();
	assert((trump_card >= gmCARD_INVALID) && (trump_card < gmTOTAL_CARDS));

	// Draw the trump only if
	// 1. There is a valid trump set
	// 2. and if the trump is not shown yet.

	if((trump_card != gmCARD_INVALID) && !m_engine.IsTrumpShown())
	{
//#ifdef raGAME_HIDE_AI_HANDS
		if(player_type == raPLAYER_TYPE_AI)
			mdc.SelectObject(*m_card_backs[m_pref_card_back]);
		else
//#endif
			mdc.SelectObject(*m_card_faces[trump_card]);

		if(!BlitToBack(raCARD_PANEL_RELIEF, raCARD_PANEL_RELIEF,
			GG_CARD_WIDTH, GG_CARD_HEIGHT, &mdc, 0, 0, wxCOPY, true))
		{
			wxLogError(String.Format(("BlitToBack failed. %s:%d"), (__FILE__), __LINE__));
			return false;
		}
	}

	return true;
}

void raGamePanel.OnInfo(raInfoEvent& event)
{
	int ret_val;
	//int dealer;
	switch(event.GetCommand())
	{
	case raINFO_CMD_NEW_DEAL:
		if(!NewDeal())
		{
			wxLogError(String.Format(("NewDeal() failed. %s:%d"), (__FILE__), __LINE__));
		}
		break;
	case raINFO_CMD_SHOW_TRUMP:
		if((ret_val = ShowTrump()) != 0)
		{
			wxMessageBox(("Cannot ask for trump"));
		}
		// Check whether the game needs to be abandoned
		if(CheckOppTrumps() == 1)
		{
			EndDeal(true);
			return;
		}
		while(Continue());
		break;
	default:
		wxLogError(String.Format(("Unrecognized info event received. %s:%d"), (__FILE__), __LINE__));
		break;
	}
}
void raGamePanel.OnBid(raBidEvent& event)
{
	int ret_val;
	int bid;

	bid = event.GetBid();

	if((ret_val = MakeBid(bid)) != 0)
	{
		// In case of error, show appropriate message
		switch(ret_val)
		{
		case gmERR_CANNOT_PASS:
			wxMessageBox(("Cannot pass."));
			break;
		case gmERR_BID_LESS_THAN_MIN:
			wxMessageBox(String.Format(("Bid less than the minimum possible")));
			break;
		default:
			wxLogError(String.Format(("Unexpected error value. %s:%d"), (__FILE__), __LINE__));
			return;
			break;
		}
	}

	// Continue the game
	while(Continue());
}

void raGamePanel.OnLeftDClick(wxMouseEvent &event)
{
	// If card play and trump selection are on double click
	if(m_play_card_on == raCONFIG_PREFS_PLAYCARDON_DCLICK)
	{
		if(!OnCardClick(event.GetPosition()))
		{
			wxLogError(String.Format(("OnCardClick failed. %s:%d"), (__FILE__), __LINE__));
			return;
		}
	}
	return;
}
void raGamePanel.OnLeftUp(wxMouseEvent &event)
{
	wxPoint pt;
	int i;

	if(m_wait_trick)
	{
		pt = event.GetPosition();
		for(i = 0; i < gmTOTAL_PLAYERS; i++)
		{
			if(m_trick_card_rects[i].Contains(pt))
			{
				gmEngine.ResetTrick(&m_trick);
				// Redraw back buffer and refrsh the screen
				// to reflect acceptance of the trick
				if(!UpdateDrawAndRefresh(false))
				{
					wxLogError(String.Format(("Call to UpdateDrawAndRefresh() failed. %s:%d"), (__FILE__), __LINE__));
				}

				m_wait_trick = false;

				// Check if the deal has ended
				if(HasDealEnded())
				{
					if(!EndDeal())
					{
						wxLogError(String.Format(("EndDeal failed. %s:%d"), (__FILE__), __LINE__));
						return;
					}
					return;
				}

				while(Continue());
				return;
			}
		}

	}
	else
	{
		// If card play and trump selection are on double click
		if(m_play_card_on == raCONFIG_PREFS_PLAYCARDON_SCLICK)
		{
			if(!OnCardClick(event.GetPosition()))
			{
				wxLogError(String.Format(("OnCardClick failed. %s:%d"), (__FILE__), __LINE__));
				return;
			}
		}
	}
	return;
}

int raGamePanel.GetCardAtPos(wxPoint pt, int loc)
{
	int i, j;

	if(loc == gmPLAYER_INVALID)
	{

		// Check whether the position is inside any of the hands
		for(i = 0; i < gmTOTAL_PLAYERS; i++)
		{
			// If so, find the card
			if(m_hand_rects[i].Contains(pt))
			{
				// Consider each of the cards in the hand.
				// This is done in reverse order is because the
				// dimension/position of each of the cards is calculated
				// without considering the possibility of overlapping
				for(j = m_hands[i].count - 1; j >= 0; j--)
				{
					if(m_hand_card_rects[i][j].Contains(pt))
					{
						//wxMessageBox(String.Format("Inside %d", m_hands[i].card_indexes[j]));
						return m_hands[i].card_indexes[j];
					}
				}
			}
		}
	}
	else
	{
		assert((loc >= 0) && (loc < gmTOTAL_PLAYERS));
		for(i = m_hands[loc].count - 1; i >= 0; i--)
		{
			if(m_hand_card_rects[loc][i].Contains(pt))
			{
				return m_hands[loc].card_indexes[i];
			}
		}
	}

	return gmCARD_INVALID;
}
int raGamePanel.GetHandAtPos(wxPoint pt)
{
	int i;

	// Check whether the position is inside any of the hands
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		// If so, return the player/location
		if(m_hand_rects[i].Contains(pt))
		{
			return i;
		}
	}

	return gmPLAYER_INVALID;

}

boolean raGamePanel.Continue()
{
	long hands[gmTOTAL_PLAYERS];
	boolean m_quit = false;
	gmOutputDealInfo out_deal_info;
	gmOutputDealEndInfo out_deal_end_info;
	gmInputBidInfo in_bid_info;
	gmInputTrumpselInfo in_trumpsel_info;
	gmInputTrickInfo in_trick_info;
	raInfoDetails info_dtls;
	String instruction;
	gmEngineData re_data;
	//gmEngineData pplay_data;
	int bid;
	int trump;
	int card;
	//int old_deal_no;
	int i;
	//String msg_game;
	String msg;
	int loc;
	//int old_pts[gmTOTAL_TEAMS];
	//int game_pts;
	boolean should_abandon;
	gmRules rules;
	long complete_hand = 0;

	// The game cannot continue if we are waiting
	// for the user to confirm a trick
	if(m_wait_trick)
		return false;

	// The game cannot continue if deal has ended
	if(m_deal_ended)
		return false;

	while(!m_quit)
	{
		if(m_engine.IsOutputPending())
		{
			switch(m_engine.GetPendingOutputType())
			{
			case gmOUTPUT_STARTED:
				m_engine.GetOutput(null, null);

				// Set the dealer in the information panel
				m_info.GetDetails(&info_dtls);
				info_dtls.dealer = m_engine.GetDealer();
				m_info.SetDetails(&info_dtls);

				// TODO : This instruction is not shown or is overwritten. Consider removal
				m_info.SetInstruction(("Deal started."), raINFO_CMD_NONE);
				break;
			case gmOUTPUT_DEAL:
				memset(&out_deal_info, 0, sizeof(gmOutputDealInfo));
				m_engine.GetOutput(null, &out_deal_info);
				m_info.SetInstruction(("Cards dealt."), raINFO_CMD_NONE);

				m_engine.GetHands(hands);
				//wxLogDebug(gmUtil.PrintHands(hands));

				// Update hands, redraw back buffer and refresh the screen
				if(!UpdateDrawAndRefresh())
				{
					wxLogError(String.Format(("UpdateDrawAndRefresh failed. %s:%d"), (__FILE__), __LINE__));
					return false;
				}

				// Check whether any of the players have all the
				// four jacks. In which case, the game is abandoned
				bid = -1;
				loc = gmPLAYER_INVALID;
				// Obtain the max bidder if any
				m_engine.GetMaxBid(&bid, &loc);

				for(i = 0; i < gmTOTAL_PLAYERS; i++)
				{
					complete_hand = hands[i];
					// If the player is the max bidder
					// add the card that is set as the trump to the compelte hand
					if(loc == i)
					{
						complete_hand |= 1 << m_engine.GetTrumpCard();
					}
					if((complete_hand & raGAME_FOUR_JACKS) == raGAME_FOUR_JACKS)
					{
						msg.Empty();
						msg.Append(gmUtil.m_long_locs[i]);
						msg.Append((" has all the four Jacks. This deal has been abandoned"));
						wxMessageBox(msg, ("Deal abandoned"), wxICON_INFORMATION);
						if(!EndDeal(true))
						{
							wxLogError(String.Format(("EndDeal failed. %s:%d"), (__FILE__), __LINE__));
							return false;
						}

					}
				}

				// Check whether any of the players have all low cards
				// after the second round of deal.
				// In which case, the game *can be* abandonded if decided by
				// the player.
				if(out_deal_info.round == gmDEAL_ROUND_2)
				{
					for(i = 0; i < gmTOTAL_PLAYERS; i++)
					{
						// If the player is the max bidder
						// add the card that is set as the trump to the compelte hand
						complete_hand = hands[i];
						if(loc == i)
						{
							complete_hand |= 1 << m_engine.GetTrumpCard();
						}

						if(!(complete_hand & raGAME_ALL_HIGH_CARDS))
						{
							should_abandon = false;
							if(m_players[i].GetType() == raPLAYER_TYPE_AI)
							{
								m_players[i].AbandonGame(&should_abandon);

							}
							else
							{
								msg.Empty();
								msg.Append(("All the cards dealt to you ("));
								msg.Append(gmUtil.m_long_locs[i]);
								msg.Append((") are low cards."));
								msg.Append(("\n"));
								msg.Append(("Do you want to abandon the deal?"));
								msg.Append(("\n\n"));
								msg.Append(("Click Yes to abandon the deal"));
								msg.Append(("\n"));
								msg.Append(("Click No to continue with the auction and deal"));
								if(wxMessageBox(msg, ("Abandon the deal?"),
									wxYES_NO | wxICON_QUESTION) == wxYES)
								{
									should_abandon = true;
								}
							}

							if(should_abandon)
							{
								if(m_players[i].GetType() == raPLAYER_TYPE_AI)
								{
									msg.Empty();
									msg.Append(("All the cards dealt to "));
									msg.Append(gmUtil.m_long_locs[i]);
									msg.Append((" are low cards (without points)"));
									msg.Append(("\n"));
									msg.Append(("The player has decided to abandon the deal"));
									wxMessageBox(msg, ("Deal abandoned"),
										wxOK | wxICON_INFORMATION);
								}
								if(!EndDeal(true))
								{
									wxLogError(String.Format(("EndDeal failed. %s:%d"),
										(__FILE__), __LINE__));
								}
								return false;
							}
						}
					}
				}

				break;
			case gmOUTPUT_DEAL_END:
				memset(&out_deal_end_info, 0, sizeof(gmOutputDealEndInfo));
				m_engine.GetOutput(null, &out_deal_end_info);
				if(!EndDeal())
				{
					wxLogError(String.Format(("EndDeal failed. %s:%d"), (__FILE__), __LINE__));
					return false;
				}

				m_quit = true;
				break;
			}
		}
		else if(m_engine.IsInputPending())
		{
			switch(m_engine.GetPendingInputType())
			{
			case gmINPUT_BID:
				if(!m_engine.GetPendingInputCriteria(null, &in_bid_info))
				{
					wxLogError(String.Format
						(("Call to gmEngine.GetPendingInputCriteria failed. %s:%d"),
						(__FILE__), __LINE__));
					return false;
				}
				// For the first bid is being made and if the the player
				// does not have any cards with points, the player can
				// choose to abandon the deal
				m_engine.GetRules(&rules);
				if(in_bid_info.min == rules.min_bid_1)
				{
					m_engine.GetHands(hands);
					if(!(hands[in_bid_info.player] & raGAME_ALL_HIGH_CARDS))
					{
						should_abandon = false;
						//can_abandon = true;
						if(m_players[in_bid_info.player].GetType() == raPLAYER_TYPE_AI)
						{
							m_players[in_bid_info.player].AbandonGame(&should_abandon);

						}
						else
						{
							msg.Empty();
							msg.Append(("All the cards dealt to you ("));
							msg.Append(gmUtil.m_long_locs[in_bid_info.player]);
							msg.Append((") are low cards."));
							msg.Append(("\n"));
							msg.Append(("Do you want to abandon the deal?"));
							msg.Append(("\n\n"));
							msg.Append(("Click Yes to abandon the deal"));
							msg.Append(("\n"));
							msg.Append(("Click No to continue and make a bid"));
							if(wxMessageBox(msg, ("Abandon the deal?"),
								wxYES_NO | wxICON_QUESTION) == wxYES)
							{
								should_abandon = true;
							}
						}

						if(should_abandon)
						{
							if(m_players[in_bid_info.player].GetType() == raPLAYER_TYPE_AI)
							{
								msg.Empty();
								msg.Append(("All the cards dealt to "));
								msg.Append(gmUtil.m_long_locs[in_bid_info.player]);
								msg.Append((" are low cards (without points)"));
								msg.Append(("\n"));
								msg.Append(("The player has decided to abandon the deal"));
								wxMessageBox(msg, ("Deal abandoned"),
									wxOK | wxICON_INFORMATION);
							}
							if(!EndDeal(true))
							{
								wxLogError(String.Format(("EndDeal failed. %s:%d"),
									(__FILE__), __LINE__));
							}
							return false;
						}
					}
				}

				// TODO : Consider whether more elaboration is required
				// TODO : Add error check
				m_info.SetInstruction(String.Format
					(("%s, please enter a bid."), gmUtil.m_long_locs[in_bid_info.player].c_str()));

				// Enable/disable relevant buttons in the bid panel
				if(!m_bid.SetPassable(in_bid_info.passable))
				{
					wxLogError(String.Format(("Call to raBid.SetPassable failed. %s:%d"),
						(__FILE__), __LINE__));
					return false;
				}

				if(!m_bid.SetMinimumBid(in_bid_info.min))
				{
					wxLogError(String.Format(("Call to raBid.SetMinimumBid failed. %s:%d"),
						(__FILE__), __LINE__));
					return false;
				}
				// Try out bidding
				// TODO : Cleanup this bloody mess!
				if(m_players[in_bid_info.player].GetType() == raPLAYER_TYPE_AI)
				{
					m_engine.GetData(&re_data);
					m_players[in_bid_info.player].SetRuleEngineData(&re_data);

					bid = raBID_INVALID;
					trump = gmSUIT_INVALID;

					m_players[in_bid_info.player].GetBid(&bid, &trump, in_bid_info.min, !in_bid_info.passable);
					assert(bid != raBID_INVALID);

					//wxMessageBox(String.Format(
					//	"Bid suggested by AI for %d is %d %d", in_bid_info.player , bid, trump));

					//if(bid != gmBID_PASS)
					//	in_bid_info.trump = trump;
					//in_bid_info.bid = bid;
					if(MakeBid(bid))
					{
						wxLogError(String.Format(("MakeBid failed. %s:%d"),
							(__FILE__), __LINE__));
					}

					break;
				}


				// Show the bid panel
				//if(!m_bid.IsShown())
				m_bid.Show(true);
				m_quit = true;
				break;
			case gmINPUT_TRUMPSEL:
				// If bid panel is shown, hid it
				//if(m_bid.IsShown())
				m_bid.Show(false);

				if(!m_engine.GetPendingInputCriteria(null, &in_trumpsel_info))
				{
					wxLogError(String.Format
						(("Call to gmEngine.GetPendingInputCriteria failed. %s:%d"),
						(__FILE__), __LINE__));
					return false;
				}

				// If the player to select the trump is an AI based player
				// obtain a bid from the player and set the same
				if(m_players[in_trumpsel_info.player].GetType() == raPLAYER_TYPE_AI)
				{
					// Obtain trump from the AI based player
					trump = m_players[in_trumpsel_info.player].GetTrump();

					// Check for error
					if(trump == gmCARD_INVALID)
					{
						wxLogError(String.Format
							(("GetBid failed. %s:%d"), (__FILE__), __LINE__));
					}
					assert((trump >= 0) && (trump < gmTOTAL_CARDS));

					// Set the trump
					if(SetTrump(trump))
					{
						wxLogError(String.Format
							(("SetTrump failed. %s:%d"), (__FILE__), __LINE__));
					}

					// Check if the opponents have trumps after the final
					// trump is set. If not, deal is abandoned
					if(CheckOppTrumps() == 1)
					{
						m_quit = true;
					}
					break;
				}

				// TODO : Consider whether more elaboration is required
				// TODO : Add error check
				m_info.SetInstruction(String.Format
					(("%s, please select a trump."), gmUtil.m_long_locs[in_trumpsel_info.player].c_str()));

				m_quit = true;
				break;

			case gmINPUT_TRICK:

				// If bid panel is shown, hide it
				//if(m_bid.IsShown())
				//{
				m_bid.Show(false);
				//	Refresh();
				//}

				if(!m_engine.GetPendingInputCriteria(null, &in_trick_info))
				{
					wxLogError(String.Format
						(("Call to gmEngine.GetPendingInputCriteria failed. %s:%d"),
						(__FILE__), __LINE__));
					return false;
				}

				if(!UpdateDrawAndRefresh(false))
				{
					wxLogError(String.Format(("UpdateDrawAndRefresh() failed. %s:%d"),
						(__FILE__), __LINE__));
				}

				// After applying the mask to the hand held by the player
				// if only one card can be played, play the same
				// without hesitation
				m_engine.GetHands(hands);
				if(
					(gmUtil.CountBitsSet(in_trick_info.mask & hands[in_trick_info.player]) == 1) &&
					(m_auto_play || (m_players[in_trick_info.player].GetType() == raPLAYER_TYPE_AI))
					)
				{
					// Begin the busy state
					if(!.wxIsBusy())
						BeginBusyState();

					card = gmUtil.HighestBitSet(in_trick_info.mask & hands[in_trick_info.player]);
					assert((card >= 0) && (card < gmTOTAL_CARDS));
					if(PlayCard(card))
					{
						wxLogError(String.Format
							(("PlayCard failed. %s:%d"), (__FILE__), __LINE__));
						wxLogError(String.Format(("Card played is %s%s"),
							gmUtil.m_suits[gmGetSuit(card)].c_str(),
							gmUtil.m_values[gmGetValue(card)].c_str()
							));
					}

					// End the busy state
					if(.wxIsBusy())
						EndBusyState();

					// Check if the deal has ended, if so quit the loop
					if(m_deal_ended)
					{
						m_quit = true;
						break;
					}
					if(m_wait_trick)
						return false;
					break;

				}

				// If the player to play the next card is AI,
				if(m_players[in_trick_info.player].GetType() == raPLAYER_TYPE_AI)
				{
					m_info.SetInstruction(String.Format(("%s is thinking..."),
						gmUtil.m_long_locs[in_trick_info.player].c_str()));

					// Begin the busy state
					if(!.wxIsBusy())
						BeginBusyState();

					m_engine.GetData(&re_data);

					//if(!HideInfo(&re_data, in_trick_info.player))
					//{
					//	wxLogError(String.Format
					//		(("HideInfo failed. %s:%d"), (__FILE__), __LINE__));
					//}

					m_players[in_trick_info.player].SetRuleEngineData(&re_data);

					card = m_players[in_trick_info.player].GetPlay();
					if(card == -2)
					{
						wxLogError(String.Format
							(("GetPlay failed. %s:%d"), (__FILE__), __LINE__));
					}
					if(card == -1)
					{
						if(ShowTrump())
						{
							wxLogError(String.Format
								(("GetPlay failed. %s:%d"), (__FILE__), __LINE__));
						}

						// Check whether opponents have trumps
						// If yes, abandon deal
						if(CheckOppTrumps() == 1)
						{
							m_quit = true;
							EndDeal(true);
							break;
						}

						// End the busy state
						if(.wxIsBusy())
							EndBusyState();

						break;
					}
					assert((card >= 0) && (card < gmTOTAL_CARDS));

					// End the busy state
					if(.wxIsBusy())
						EndBusyState();

					if(PlayCard(card))
					{
						wxLogError(String.Format
							(("PlayCard failed. %s:%d"), (__FILE__), __LINE__));
						wxLogError(String.Format(("Card played is %s%s"),
							gmUtil.m_suits[gmGetSuit(card)].c_str(),
							gmUtil.m_values[gmGetValue(card)].c_str()
							));
					}

					if(m_wait_trick)
						return false;
					break;

				}
				// TODO : Consider whether more elaboration is required
				// TODO : Add error check
				if(m_play_card_on == raCONFIG_PREFS_PLAYCARDON_SCLICK)
					instruction.sprintf(("%s, please click on the card that you want to play."),
						gmUtil.m_long_locs[in_trick_info.player].c_str());
				else
					instruction.sprintf(("%s, please double-click on the card that you want to play."),
						gmUtil.m_long_locs[in_trick_info.player].c_str());

				if(in_trick_info.can_ask_trump)
				{
					instruction.Append((" Or ask for trump by clicking on the button below."));
					m_info.SetInstruction(instruction, raINFO_CMD_SHOW_TRUMP);
				}
				else
				{
					m_info.SetInstruction(instruction);
				}
				m_quit = true;
				break;
			}
		}
		// If the current deal has finished, no need to keep on continuing
		//else if(m_deal_ended)
		if(m_deal_ended)
		{
			m_quit = true;
			break;
		}
		while(m_engine.Continue());
	}
	return false;
}

boolean raGamePanel.ResetDeal()
{
	int i, j;

	if(!m_engine.Reset())
	{
		wxLogError(String.Format(("Attempt to reset the rule engine failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	m_wait_trick = false;
	m_deal_ended = false;

	// Initialize the hand information to zeros
	memset(m_hands, 0, sizeof(m_hands));

	// Initialize the trick
	gmEngine.ResetTrick(&m_trick);

	// Initialize the position/dimentions of hands
	// and the cards in the trick
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		m_hand_rects[i] = wxRect(0, 0, 0, 0);
		m_trick_card_rects[i] = wxRect(0, 0, 0, 0);
		//m_trick_cards[i] = gmCARD_INVALID;
	}

	// Initialize card positions and dimensions
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
		for(j = 0; j < raMAX_CARDS_PER_HAND; j++)
			m_hand_card_rects[i][j] = wxRect(0, 0, 0, 0);

	// Reset bid history
	m_bid_history.Empty();
	return true;
}
boolean raGamePanel.ResetGame()
{
	int i;
	raConfData data;

	memset(&m_saved_rules, 0, sizeof(gmRules));

	raConfig.GetInstance().GetData(&data);

	//m_orientation = raGAME_ORIENT_ALL_HORZ;
	m_orientation = raGAME_ORIENT_MIXED;

	ResetDeal();

	SetClockwise(data.game_data.clockwise);

	// Update status bar with details
	if(data.game_data.clockwise)
	{
		gmUtil.SetStatusText(raTEXT_CLOCKWISE, raSBARPOS_CLOCK);
	}
	else
	{
		gmUtil.SetStatusText(raTEXT_ANTICLOCKWISE, raSBARPOS_CLOCK);
	}

	m_engine.SetDealer(0);

	m_engine.SetMinBid(raBID_ROUND_3, data.game_data.min_bid3);
	m_engine.SetWaiveRuleFour(data.game_data.waive_rule4);
	m_engine.SetSluffJacks(data.game_data.sluff_jacks);

	// Save the rules
	m_saved_rules.min_bid_3 = data.game_data.min_bid3;
	if(data.game_data.clockwise)
		m_saved_rules.rot_addn = 1;
	else
		m_saved_rules.rot_addn = 3;
	m_saved_rules.sluff_jacks = data.game_data.sluff_jacks;
	m_saved_rules.waive_rule_4 = data.game_data.waive_rule4;

	for(i = 0; i < gmTOTAL_TEAMS; i++)
		m_game_pts[i] = 5;
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		m_pnlties[i] = 0;
		m_players[i].Reset();
		m_players[i].SetRules(&m_saved_rules);
	}

	return true;
}


boolean raGamePanel.UpdateHands(long *hands)
{
	int i, j;
	long cards_left;
	//int temp;

	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		m_hands[i].cards = hands[i];

		memset(m_hands[i].card_indexes, 0, sizeof(m_hands[i].card_indexes));

		cards_left = hands[i];
		j = 0;
		while(cards_left > 0)
		{
			m_hands[i].card_indexes[j] = gmUtil.HighestBitSet(cards_left);
			cards_left &= ~(1 << m_hands[i].card_indexes[j]);
			j++;
		}
		m_hands[i].count = j;
		assert((m_hands[i].count >= 0) && (m_hands[i].count <= raMAX_CARDS_PER_HAND));

	}
	return true;
}
// TODO : Implement the usage of loc
int raGamePanel.PlayCard(int card, int loc)
{
	gmInputTrickInfo trick_info;
	int trick_round;
	int ret_val;
	//raInfoDetails info_details;
	gmEngineData pplay_data;
	gmEngineData re_data;
	int i;

	// Validate the input card
	assert((card >= 0) && (card < gmTOTAL_CARDS));

	if(!m_engine.GetPendingInputCriteria(null, &trick_info))
	{
		wxLogError(String.Format(("Failed to get pending input criteria. %s:%d"),
			(__FILE__), __LINE__));
		return -1;
	}

	// Check whether the card is valid as per the mask
	if(!(trick_info.mask & (1 << card)))
	{
		assert(trick_info.rules);
		return gmERR_TRICK_MASK_MISMATCH;
	}

	// Save the trick round
	trick_round = m_engine.GetTrickRound();

	m_engine.GetData(&re_data);

	// Post the input and check for error
	trick_info.card = card;
	if((ret_val = m_engine.PostInputMessage(gmINPUT_TRICK, &trick_info)) != 0)
	{
		return ret_val;
	}

	wxLogMessage(String.Format(("%s plays %s%s"),
		gmUtil.m_long_locs[trick_info.player].c_str(),
		gmUtil.m_suits[gmGetSuit(card)].c_str(),
		gmUtil.m_values[gmGetValue(card)].c_str()
		));


	// Update other players about the game play
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		// Update only if the player is AI
		if((m_players[i].GetType() == raPLAYER_TYPE_AI) &&
			(re_data.in_trick_info.player != i))
		{
			// Hide information before sending
			memcpy(&pplay_data, &re_data,
				sizeof(gmEngineData));
			HideInfo(&pplay_data, i);

			// Inferences could be made from the mask or
			//the rules and hence hide the same
			pplay_data.in_trick_info.mask = 0;
			pplay_data.in_trick_info.rules = 0;
			m_players[i].PostPlayUpdate(&pplay_data, card);
			memcpy(&pplay_data, &re_data,
				sizeof(gmEngineData));
			m_players[i].CheckAssumptions(&pplay_data);
		}
	}

	// If the card played was accepted by the engine,

	// Update the cards in the trick for the round saved
	// so that the same is reflected when the back buffer
	// is redrawn
	m_engine.GetTrick(trick_round, &m_trick);

	// Update hands, redraw back buffer and refresh the screen
	if(!UpdateDrawAndRefresh())
	{
		wxLogError(String.Format(("UpdateDrawAndRefresh failed. %s:%d"), (__FILE__), __LINE__));
		return -1;
	}

	// If the trick that ended, need to wait for the user to accept the trick
	if(m_trick.count == gmTOTAL_PLAYERS)
	{
		m_info.SetInstruction(("Click on the cards to continue."), raINFO_CMD_NONE);
		m_wait_trick = true;
		// Log details of the trick ended
		wxLogMessage(String.Format(("Trick %d won by %s"), trick_round + 1,
			gmUtil.m_long_locs[m_trick.winner].c_str()));
	}

	return 0;
}

// TODO : Implement loc
int raGamePanel.SetTrump(int card, int loc)
{
	gmInputTrumpselInfo trumpsel_info;
	int ret_val;

	if(!m_engine.GetPendingInputCriteria(null, &trumpsel_info))
	{
		wxLogError(String.Format(("Failed to get pending input criteria. %s:%d"),
			(__FILE__), __LINE__));
		return -1;
	}

	trumpsel_info.card = card;
	// Post the input and check for error
	if((ret_val = m_engine.PostInputMessage(gmINPUT_TRUMPSEL, &trumpsel_info)) != 0)
	{
		wxLogError(String.Format(("PostInputMessage failed. %s:%d"),
			(__FILE__), __LINE__));
		return ret_val;
	}

	// Update hands, redraw back buffer and refresh the screen
	if(!UpdateDrawAndRefresh())
	{
		wxLogError(String.Format(("UpdateDrawAndRefresh failed. %s:%d"), (__FILE__), __LINE__));
		return -1;
	}

	return 0;
}

int raGamePanel.ShowTrump(int loc)
{
	int ret_val;
	gmInputTrickInfo in_trick_info;
	raInfoDetails info_dtls;
	int player;
	String msg;

	m_engine.GetPendingInputCriteria(null, &in_trick_info);
	// Check whether trump can be asked gmERR_TRICK_INVALID_TRUMP_REQ
	if(loc != gmPLAYER_INVALID)
	{
		assert((loc >= 0) && (loc < gmTOTAL_PLAYERS));
		if(in_trick_info.player != loc)
		{
			wxLogError(String.Format(("Invalid request for trump to be shown. %s:%d"), (__FILE__), __LINE__));
			return gmERR_TRICK_INVALID_TRUMP_REQ;
		}
	}

	in_trick_info.ask_trump = true;
	if((ret_val = m_engine.PostInputMessage(gmINPUT_TRICK, &in_trick_info)) != 0)
	{
		wxLogError(String.Format(("PostInputMessage failed. %s:%d"), (__FILE__), __LINE__));
		return ret_val;
	}

	player = in_trick_info.player;
	msg.Append(String.Format(("%s asked for trump."), gmUtil.m_long_locs[player].c_str()));
	msg.Append(String.Format(("\nTrump is %s(%s)."),
		gmUtil.m_suits[m_engine.GetTrump()].c_str(),
		gmUtil.m_values[gmGetValue(m_engine.GetTrumpCard())].c_str() ));
	wxMessageBox(msg, ("Trump Shown"), wxOK | wxICON_INFORMATION);

	// Reflect the trump in the info planel
	m_info.GetDetails(&info_dtls);
	info_dtls.trump = m_engine.GetTrump();
	m_info.SetDetails(&info_dtls);

	// Update hands, redraw back buffer and refresh the screen
	if(!UpdateDrawAndRefresh())
	{
		wxLogError(String.Format(("UpdateDrawAndRefresh failed. %s:%d"), (__FILE__), __LINE__));
		return -1;
	}

	return 0;
}

int raGamePanel.MakeBid(int bid, int loc)
{
	gmInputBidInfo bid_info;
	int ret_val;
	raInfoDetails info_dtls;
	raBackDrawInfo back_draw_info;

	// Check if the engine is waiting for a bid input
	if((m_engine.IsInputPending()) && (m_engine.GetPendingInputType() == gmINPUT_BID))
	{
		if(!m_engine.GetPendingInputCriteria(null, &bid_info))
		{
			wxLogError(String.Format(("Failed to get pending input criteria. %s:%d"), (__FILE__), __LINE__));
			return -1;
		}
		// Check if the bid is by the correct player
		if((loc != gmPLAYER_INVALID) && (loc != bid_info.player))
		{
			wxLogDebug(String.Format(("Bid by the wrong player. %s:%d"),
				(__FILE__), __LINE__));
			return gmERR_BID_BY_WRONG_PLAYER;
		}

		// If the bid is a pass,
		if((bid == gmBID_PASS) && (!bid_info.passable))
		{
			wxLogDebug(String.Format(("Cannot pass. %s:%d"),
				(__FILE__), __LINE__));
			return gmERR_CANNOT_PASS;
		}

		// If the bid is less than minimum
		if((bid != gmBID_ALL) && (bid != gmBID_PASS) && (bid < bid_info.min))
		{
			wxLogDebug(String.Format(("Bid less than minimum allowed. %s:%d"),
				(__FILE__), __LINE__));
			return gmERR_BID_LESS_THAN_MIN;
		}

		bid_info.bid = bid;

		// Post the input and check for error
		if((ret_val = m_engine.PostInputMessage(gmINPUT_BID, &bid_info)) != 0)
		{
			wxLogDebug(String.Format(("PostInputMessage failed. %s:%d"),
				(__FILE__), __LINE__));
			return ret_val;
		}

		// Append to the auciton history
		if(bid != gmBID_PASS)
		{
			String temp;
			if(bid == gmBID_ALL)
			{
				temp.Append(("All Tricks"));
			}
			else
			{
				temp.Append(String.Format(("%d"), bid_info.bid));
			}
			temp.Append((" by ") + gmUtil.m_long_locs[bid_info.player] + ("\n"));

			// Log the bid
			wxLogMessage(temp.Trim());

			m_bid_history.Append(temp);
		}

	}
	else
	{
		wxLogDebug(String.Format((
			"Cannot make a bid when one is not expected. %s:%d"),
			(__FILE__), __LINE__));
		return -1;
	}

	// Update Info panel
	if(bid != gmBID_PASS)
	{
		m_info.GetDetails(&info_dtls);
		info_dtls.bid = bid;
		info_dtls.bidder = bid_info.player;
		m_info.SetDetails(&info_dtls);
	}

	//
	// Show the bid graphically in a bubble
	//

	back_draw_info.draw_bid = true;
	back_draw_info.bid = bid_info.bid;
	back_draw_info.bid_loc = bid_info.player;

	if(m_show_bidbubbles && (bid != gmBID_PASS))
	{
		// Hide the bid window
		m_bid.Show(false);

		// Redraw the screen with bid bubble
		if(!UpdateDrawAndRefresh(false, &back_draw_info))
		{
			wxLogError(String.Format(("UpdateDrawAndRefresh failed. %s:%d"), (__FILE__), __LINE__));
		}

		// Sleep for some time so that the user can
		// read the information
		wxThread.Sleep(1000);

	}

	// Redraw the screen without bid bubble
	if(!UpdateDrawAndRefresh())
	{
		wxLogError(String.Format(("UpdateDrawAndRefresh failed. %s:%d"), (__FILE__), __LINE__));
	}

	return 0;
}


boolean raGamePanel.UpdateDrawAndRefresh(boolean update, raBackDrawInfo *info)
{
	long hands[gmTOTAL_PLAYERS];

	if(update)
	{
		m_engine.GetHands(hands);
		//wxLogDebug(String.Format("%s\n %s:%d", gmUtil.PrintHands(hands).c_str(), (__FILE__), __LINE__));
		// Update hand/card positions and dimensions
		if(!UpdateHands(hands))
		{
			wxLogError(String.Format(("Call to UpdateHands failed. %s:%d"), (__FILE__), __LINE__));
			return false;
		}
	}

	// Redraw back buffer and refrsh the screen
	// to reflect the card play
	if(!RedrawBack(info))
	{
		wxLogError(String.Format(("RedrawBack failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	if(!RefreshScreen())
	{
		wxLogError(String.Format(("RefreshScreen failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	this.Update();
	return true;
}

boolean raGamePanel.HideInfo(gmEngineData *data, int player)
{
	int i;

	assert((player >= 0) && (player < gmTOTAL_PLAYERS));

	// Hide information that is not known the player
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		if(i != player)
		{
			data.hands[i] = 0;
		}
	}

	if((data.curr_max_bidder != player) && !data.trump_shown)
	{
		data.trump_card = gmCARD_INVALID;
		data.trump_suit = gmSUIT_INVALID;
	}

	return true;
}
boolean raGamePanel.HasDealEnded(int *winner)
{
	int bid, loc;
	int pts[gmTOTAL_TEAMS];

	if(!m_engine.GetMaxBid(&bid, &loc))
	{
		wxLogError(String.Format(("GetMaxBid failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	m_engine.GetPoints(pts);

	// Check if the max bidders team has won the deal
	if(pts[gmGetTeam(loc)] >= bid)
	{
		if(winner)
			*winner = gmGetTeam(loc);
		return true;
	}
	if(pts[gmGetOpponent(gmGetTeam(loc))] > (28 - bid))
	{
		if(winner)
			*winner = gmGetOpponent(gmGetTeam(loc));
		return true;
	}
	return false;

}
boolean raGamePanel.EndDeal(boolean abandon)
{
	int bid, loc;
	raInfoDetails info_dtls;
	int game_pts;
	int winner;
	String msg_deal;
	String msg_pnlty;
	int i;
	int old_deal_no;


	// If the game is not being abandoned,
	// show the detail of the deal that has ended.
	if(!abandon)
	{
		if(!HasDealEnded(&winner))
		{
			wxLogError(String.Format(("HasDealEnded failed. %s:%d"), (__FILE__), __LINE__));
			return false;
		}

		if(!m_engine.GetMaxBid(&bid, &loc))
		{
			wxLogError(String.Format(("GetMaxBid failed. %s:%d"), (__FILE__), __LINE__));
			return false;
		}
	}

	m_info.GetDetails(&info_dtls);

	if(!abandon)
	{
		// TODO : Remove hard coding
		// Calculate the game points to be awarded
		// and set it in the info details
		if(bid < 20)
			game_pts = 1;
		else if(bid < 23)
			game_pts = 2;
		else
			game_pts = 3;

		msg_pnlty = ("");
		// If the max bidder is the winner and he has at least one
		// penalty, one will be deducted
		if(winner == gmGetTeam(loc))
		{
			if(m_pnlties[loc] > 0)
			{
				m_pnlties[loc]--;
				msg_pnlty.Append(("One penalty deducted from "));
				msg_pnlty.Append(gmUtil.m_short_locs[loc]);
				msg_pnlty.Append(("."));
			}
		}
		else
			game_pts++;

		m_game_pts[winner] += game_pts;
		m_game_pts[gmGetOpponent(winner)] -= game_pts;

		for(i = 0; i < gmTOTAL_TEAMS; i++)
		{
			if(m_game_pts[i] < 0)
			{
				m_pnlties[i]++;
				// TODO : Use raGetPartner
				m_pnlties[i + 2]++;
				// TODO : Remove hard coding
				m_game_pts[0] = 5;
				m_game_pts[1] = 5;

				if(!msg_pnlty.IsEmpty())
					msg_pnlty.Append(("\n"));
				msg_pnlty.Append(("Game points reset and penalties awarded to "));
				msg_pnlty.Append(gmUtil.m_short_teams[i]);
				msg_pnlty.Append(("."));
				break;
			}
		}

		// Show a message box with details
		// as to who won the deal

		msg_deal = ("");
		msg_deal.Append(String.Format(("Game won by %s."),
			gmUtil.m_short_teams[winner].c_str()));
		msg_deal.Append(String.Format(("\n\nHighest bid was %d by %s."),
			bid, gmUtil.m_long_locs[loc].c_str()));
		msg_deal.Append(String.Format(("\n%s won %d points."),
			gmUtil.m_short_teams[0].c_str(), m_engine.GetPoints(0)));
		msg_deal.Append(String.Format(("\n%s won %d points."),
			gmUtil.m_short_teams[1].c_str(), m_engine.GetPoints(1)));

		msg_deal.Append(String.Format(("\n\n%d game point(s) awarded to %s."),
			game_pts,
			gmUtil.m_short_teams[winner].c_str()));

		if(!msg_pnlty.IsEmpty())
		{
			msg_deal.Append(("\n\n"));
			msg_deal.Append(msg_pnlty);
		}

		.wxMessageBox(msg_deal, ("Deal completed"), wxOK | wxICON_INFORMATION);
		wxLogMessage(msg_deal);
	}

	// End the busy state
	if(.wxIsBusy())
		EndBusyState();

	// Reset all players
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		m_players[i].Reset();
		m_players[i].SetRules(&m_saved_rules);
	}

	m_info.SetInstruction((""),
		raINFO_CMD_NEW_DEAL);

	// Set the dealer for the next deal
	// TODO : +1 is hardcoded.
	if(m_clockwise)
		m_engine.SetDealer((m_engine.GetDealer() + 1) % gmTOTAL_PLAYERS);
	else
		m_engine.SetDealer((m_engine.GetDealer() + 3) % gmTOTAL_PLAYERS);

	// Set the detail in the info panel
	old_deal_no = info_dtls.deal_no;
	m_info.ResetDetails();
	m_info.GetDetails(&info_dtls);
	info_dtls.dealer = m_engine.GetDealer();
	info_dtls.deal_no = old_deal_no + 1;
	for(i = 0; i < gmTOTAL_TEAMS; i++)
		info_dtls.points[i] = m_game_pts[i];
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
		info_dtls.pnlties[i] = m_pnlties[i];
	m_info.SetDetails(&info_dtls);

	m_deal_ended = true;
	return true;
}
boolean raGamePanel.BeginBusyState()
{
	.wxBeginBusyCursor();
	return true;
}
boolean raGamePanel.EndBusyState()
{
	.wxEndBusyCursor();
	return true;
}
int raGamePanel.CheckOppTrumps()
{
	gmEngineData data;
	String msg;
	int i;
	long opp_hands;

	if(m_engine.GetStatus() == gmSTATUS_TRICKS)
	{
		m_engine.GetData(&data);
		assert((data.curr_max_bidder >= 0) && (data.curr_max_bidder < gmTOTAL_PLAYERS));

		// Combine the hands of opponents of the max bidder
		opp_hands = 0;
		i = gmGetOpponentOne(data.curr_max_bidder);
		opp_hands |= data.hands[i] | data.played_cards[i];
		i = gmGetOpponentTwo(data.curr_max_bidder);
		opp_hands |= data.hands[i] | data.played_cards[i];

		// If the opponents of the max bidder does not
		// a single trump card among them, abandon deal
		// and show appropriate message
		if(!(opp_hands & gmUtil.m_suit_mask[data.trump_suit]))
		{
			msg.Empty();
			msg.Append(("The trump selected for the deal is "));
			msg.Append(gmUtil.m_suits[data.trump_suit]);
			msg.Append(("\n"));
			msg.Append(("Team "));
			msg.Append(gmUtil.m_short_teams[gmGetOpponent(data.curr_max_bidder)]);
			msg.Append((" does not have any card of this suit"));
			msg.Append(("\n\n"));
			msg.Append(("This deal has been abandoned"));
			wxMessageBox(msg, ("Deal abandoned"), wxOK | wxICON_INFORMATION);

			if(!EndDeal(true))
			{
				wxLogError(String.Format(("EndDeal failed. %s:%d"), (__FILE__), __LINE__));
				return -1;
			}

			return 1;
		}
	}

	return 0;
}

boolean raGamePanel.OnCardClick(wxPoint pt)
{
	gmInputTrumpselInfo trumpsel_info;
	gmInputTrickInfo trick_info;
	int ret_val;
	int card;
	int loc;
	int status;
	//long hands[gmTOTAL_PLAYERS];
	//long opp_hands;
	//int i;
	//String msg;
	//int opp_team;

	// Check if the engine is waiting for input on trump selection
	if(m_engine.IsInputPending())
	{
		switch(m_engine.GetPendingInputType())
		{
		case gmINPUT_TRUMPSEL:
			if(!m_engine.GetPendingInputCriteria(null, &trumpsel_info))
			{
				wxLogError(String.Format(("Failed to get pending input criteria. %s:%d"),
					(__FILE__), __LINE__));
				return false;
			}

			// Save the status of the rule engine
			status = m_engine.GetStatus();

			// Obtain the hand inside which the click was received
			loc = GetHandAtPos(pt);

			// Check whether the double-click was received inside a hand
			if(loc == gmPLAYER_INVALID)
			{
				return true;
			}

			// Check whether the double-click was received at the correct hand
			if(trumpsel_info.player != loc)
			{
				return true;
			}

			// Obtain the card at the position where the double click was received
			card = GetCardAtPos(pt);
			if(card == gmCARD_INVALID)
			{
				wxLogDebug(String.Format(("Double click received, but not on a card. %s:%d"),
					(__FILE__), __LINE__));
				return true;
			}

			wxLogDebug(String.Format(("Card index %d"), card));

			// Post the input and check for error
			if((ret_val = SetTrump(card)) != 0)
			{
				// In case of error, show appropriate message
				// TODO : Add relevant cases
				//switch(ret_val)
				//{
				//default:
				wxLogError(String.Format(("Unexpected error value. %s:%d"), (__FILE__), __LINE__));
				return false;
				//	break;
				//}
			}

			// Once the final trump is selected check if the
			// opponents of the max bidder has atleast one trump
			// among themselves. If not, game is abandoned.
			if(CheckOppTrumps() == 1)
				return true;

			// Continue the game
			while(Continue());

			break;
		case gmINPUT_TRICK:
			if(!m_engine.GetPendingInputCriteria(null, &trick_info))
			{
				wxLogError(String.Format(("Failed to get pending input criteria. %s:%d"),
					(__FILE__), __LINE__));
				return false;
			}
			// Obtain the hand inside which the click was received
			loc = GetHandAtPos(pt);

			// Check whether the double-click was received inside a hand
			if(loc == gmPLAYER_INVALID)
			{
				wxLogDebug(String.Format(("Double click received, but not inside a hand. %s:%d"),
					(__FILE__), __LINE__));
				return true;
			}

			// Check whether the double-click was received at the correct hand
			if(trick_info.player != loc)
			{
				wxLogDebug(String.Format(("Double click received, but at the wrong hand. %s:%d"),
					(__FILE__), __LINE__));
				return true;
			}

			// Obtain the card at the position where the double click was received
			card = GetCardAtPos(pt);
			if(card == gmCARD_INVALID)
			{
				wxLogError(String.Format(("Double click received inside a hand, but not on a card. %s:%d"),
					(__FILE__), __LINE__));
				return true;
			}

			// Post the input and check for error
			if((ret_val = PlayCard(card)) != 0)
			{
				// In case of error, show appropriate message
				// TODO : Add relevant cases
				switch(ret_val)
				{
				case gmERR_TRICK_MASK_MISMATCH:

					assert(trick_info.rules);
					// TODO : More appropriate messages

					// If the card did not match the mask
					// show an error message depending on the rule
					// that was used to create the mask
					if(trick_info.rules & gmRULE_1)
					{
						wxMessageBox(("Player who set the trump cannot lead trump"));
						return true;
					}
					else if(trick_info.rules & gmRULE_2)
					{
						wxMessageBox(("After trump was asked, trump should be played"));
						return true;
					}
					else if(trick_info.rules & gmRULE_3)
					{
						wxMessageBox(("Should follow lead suit"));
						return true;
					}
					else if(trick_info.rules & gmRULE_4)
					{
						wxMessageBox(("After the trump was shown, the same card should be played"));
						return true;
					}
					else if(trick_info.rules & gmRULE_5)
					{
						wxMessageBox(("Should not sluff a Jack"));
						return true;
					}

					wxLogError(String.Format(("None of the rules matched. %s:%d"), (__FILE__), __LINE__));

					wxMessageBox(("Card does not match mask"));
					return true;
					break;
				default:
					wxLogError(String.Format(("Unexpected error value. %s:%d"), (__FILE__), __LINE__));
					return false;
					break;
				}
			}

			// Continue the game
			while(Continue());
			break;
		default:
			wxLogDebug(String.Format(("Unexpected type of pending input. %s:%d"), (__FILE__), __LINE__));
			break;
		}
	}
	else
		wxLogDebug(String.Format(("Bid event received when not expected. %s:%d"), (__FILE__), __LINE__));

	return true;
}



