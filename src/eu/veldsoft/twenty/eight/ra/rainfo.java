

//




//




//



package eu.veldsoft.twenty.eight.ra;

//#ifndef _RAINFO_H
//#define _RAINFO_H

//#include "ra/racommon.h"
//#include "gm/gmutil.h"

//#ifdef raREAD_SEED_FROM_FILE
//#include "wx/wfstream.h"
//#include "wx/fileconf.h"
//#endif

enum
{
	raINFO_CMD_NONE = 1,
	raINFO_CMD_NEW_DEAL,
	raINFO_CMD_SHOW_TRUMP
};

//#define raINFO_SHOW_TRUMP_TEXT ("Show Trump")
//#define raINFO_DEAL_TEXT ("New Deal")

class tagraINFO_DETAILS
{
	int deal_no;
	int dealer;
	int bid;
	int bidder;
	int trump;
	int points[gmTOTAL_TEAMS];
	int pnlties[gmTOTAL_PLAYERS];
}raInfoDetails;

class raGamePanel;

class raInfo: public wxPanel
{
private:
	DECLARE_EVENT_TABLE()

	// Disallow copy constructor/assignment operators
	raInfo(final raInfo &);
    raInfo & operator=(final raInfo &);

	wxButton *m_button;
	wxStaticText *m_dealno;
	wxStaticText *m_dealer;
	wxStaticText *m_bid;
	wxStaticText *m_trump;

	wxStaticText *m_nspts;
	wxStaticText *m_ewpts;

	wxStaticText *m_spnlty;
	wxStaticText *m_npnlty;
	wxStaticText *m_epnlty;
	wxStaticText *m_wpnlty;

	wxStaticText *m_instr;


	int m_curr_cmd;
	raGamePanel *m_game;
	raInfoDetails m_details;
	String m_instruction;

	void OnButtonClick(wxCommandEvent &event);
public:
	raInfo(wxWindow* parent);
	~raInfo();
	boolean SetDetails(raInfoDetails *details);
	void GetDetails(raInfoDetails *details);
	boolean SetInstruction(String instruction, int cmd = raINFO_CMD_NONE);
	boolean SetGamePanel(raGamePanel *game_panel);
	boolean ResetDetails(boolean refresh = false);
};

//#endif


//




//




//



//#include "ra/rainfo.h"
//#include "ra/ragamepanel.h"

BEGIN_EVENT_TABLE(raInfo, wxPanel)
	EVT_BUTTON(XRCID("m_rainfo_btn"), raInfo.OnButtonClick)
END_EVENT_TABLE()

raInfo.raInfo(wxWindow* parent)//: wxPanel((wxWindow*)parent)
{
	m_game = null;
	m_curr_cmd = raINFO_CMD_NONE;

	ResetDetails();

	if (!wxXmlResource.Get().LoadPanel(this, parent, ("raInfo")))
		wxLogError(("Missing wxXmlResource.Get().Load() in OnInit()?"));

	m_button = XRCCTRL(*this, "m_rainfo_btn", wxButton);
	m_dealno = XRCCTRL(*this, "m_rainfo_dealno", wxStaticText);
	m_dealer = XRCCTRL(*this, "m_rainfo_dealer", wxStaticText);
	m_bid = XRCCTRL(*this, "m_rainfo_bid", wxStaticText);
	m_trump = XRCCTRL(*this, "m_rainfo_trump", wxStaticText);

	m_nspts = XRCCTRL(*this, "m_rainfo_nspts", wxStaticText);
	m_ewpts = XRCCTRL(*this, "m_rainfo_ewpts", wxStaticText);


	m_spnlty = XRCCTRL(*this, "m_rainfo_spnlty", wxStaticText);
	m_npnlty = XRCCTRL(*this, "m_rainfo_npnlty", wxStaticText);
	m_epnlty = XRCCTRL(*this, "m_rainfo_epnlty", wxStaticText);
	m_wpnlty = XRCCTRL(*this, "m_rainfo_wpnlty", wxStaticText);

	m_instr = XRCCTRL(*this, "m_rainfo_instr", wxStaticText);

	m_button.Show(false);


	SetDetails(&m_details);

	return;

}
raInfo.~raInfo()
{
	return;
}

boolean raInfo.SetDetails(raInfoDetails *details)
{

	// Validate input data and set the deal number
	if(details.deal_no < 0)
	{
		wxLogError(String.Format(("Negative deal number passed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	m_dealno.SetLabel(String.Format(("%d"), details.deal_no));

	// Validate input data and set the dealer
	if((details.dealer < gmPLAYER_INVALID) || (details.dealer > gmTOTAL_PLAYERS))
	{
		wxLogError(String.Format(("Incorrect dealer. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(details.dealer == gmPLAYER_INVALID)
		m_dealer.SetLabel(("N/A"));
	else
		m_dealer.SetLabel((String.Format(("%s"), gmUtil.m_long_locs[details.dealer].c_str())));


	// Validate input data and set the bidder
	if((details.bidder < gmPLAYER_INVALID) || (details.bidder > gmTOTAL_PLAYERS))
	{
		wxLogError(String.Format(("Incorrect bidder. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(details.bidder == gmPLAYER_INVALID)
		m_bid.SetLabel(("N/A"));
	else if (details.bid == gmBID_ALL)
		m_bid.SetLabel(String.Format(("All by %s"), gmUtil.m_long_locs[details.bidder].c_str()));
	else
		m_bid.SetLabel(String.Format(("%d by %s"), details.bid, gmUtil.m_long_locs[details.bidder].c_str()));

	// Validate input data and set the trump
	if((details.trump < gmSUIT_INVALID) || (details.trump > gmTOTAL_SUITS))
	{
		wxLogError(String.Format(("Incorrect trump suit. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(details.trump == gmSUIT_INVALID)
		m_trump.SetLabel(String.Format(("Not Shown")));
	else
		m_trump.SetLabel(String.Format(("%s"), gmUtil.m_suits[details.trump].c_str()));

	m_nspts.SetLabel(String.Format(("%d"), details.points[0]));
	m_ewpts.SetLabel(String.Format(("%d"), details.points[1]));

	m_spnlty.SetLabel(String.Format(("%s-%d"),
		gmUtil.m_short_locs[0].c_str(), details.pnlties[0]));
	m_wpnlty.SetLabel(String.Format(("%s-%d"),
		gmUtil.m_short_locs[1].c_str(), details.pnlties[1]));
	m_npnlty.SetLabel(String.Format(("%s-%d"),
		gmUtil.m_short_locs[2].c_str(), details.pnlties[2]));
	m_epnlty.SetLabel(String.Format(("%s-%d"),
		gmUtil.m_short_locs[3].c_str(), details.pnlties[3]));

	memcpy(&m_details, details, sizeof(raInfoDetails));
	return true;
}
void raInfo.GetDetails(raInfoDetails *details)
{
	memcpy(details, &m_details, sizeof(raInfoDetails));
}

boolean raInfo.SetInstruction(String instruction, int cmd)
{
	if((cmd != m_curr_cmd) || (cmd == raINFO_CMD_NONE))
	{
		m_instruction = instruction;

		// Depending on the command id passed,
		// enable/disable the command button
		// and set it's text accordingly
		switch(cmd)
		{
		case raINFO_CMD_NONE:
			m_button.Show(false);
			break;
		case raINFO_CMD_NEW_DEAL:
			m_button.Show(true);
			m_button.SetLabel(raINFO_DEAL_TEXT);
			if(m_instruction.IsEmpty())
				m_instruction = ("Click on the button below to start a new Deal.");
			break;
		case raINFO_CMD_SHOW_TRUMP:
			m_button.Show(true);
			m_button.SetLabel(raINFO_SHOW_TRUMP_TEXT);
			break;
		default:
			wxLogError(String.Format(("Unexpected value in switch statement. %s:%d"), (__FILE__), __LINE__));
			return false;
		}
		m_curr_cmd = cmd;
	}

	// Set the instuction text, wrap and fit

	m_instr.SetLabel(m_instruction);
	//TODO : Remove hardcoding of 10
	m_instr.Wrap(this.GetClientSize().GetWidth() - (2 * 10));
	m_instr.Update();
	m_instr.Refresh();


	return true;
}
boolean raInfo.SetGamePanel(raGamePanel *game_panel)
{
	assert(game_panel);
	m_game = game_panel;
	return true;
}

boolean raInfo.ResetDetails(boolean refresh)
{
	int i;
	m_details.bid = 0;
	m_details.bidder = gmPLAYER_INVALID;
	m_details.dealer = gmPLAYER_INVALID;
	m_details.deal_no = 1;
	for(i = 0; i < gmTOTAL_TEAMS; i++)
	{
		m_details.points[i] = 5;
	}
	for(i = 0; i < gmTOTAL_PLAYERS; i++)
	{
		m_details.pnlties[i] = 0;
	}
	m_details.trump = gmSUIT_INVALID;
	if(refresh)
	{
		SetDetails(&m_details);
	}
	return true;
}

//
// private methods
//

void raInfo.OnButtonClick(wxCommandEvent &event)
{
	assert(m_game);
	assert(m_curr_cmd != raINFO_CMD_NONE);

	raInfoEvent new_event;
	new_event.SetCommand(m_curr_cmd);
	m_game.AddPendingEvent(new_event);
}
