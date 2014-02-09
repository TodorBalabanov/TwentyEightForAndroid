

//




//




//



package eu.veldsoft.twenty.eight.ra;

//#ifndef _RABID_H
//#define _RABID_H

//#include "ra/racommon.h"

//#define raBID_BTN_ROWS 5
//#define raBID_BTN_COLS 3
//#define raBID_TOTAL_BTNS 15

//#define raBID_BTN_ID_START 100
//#define raBID_BTN_ID_MAX (raBID_BTN_ID_START + raBID_TOTAL_BTNS - 1)
//#define raBID_BTN_ID_ALL 150
//#define raBID_BTN_ID_PASS 151

//#define raBID_MIN_BTN_WIDTH 10

//#define raGetBidFromId(X) (X - raBID_BTN_ID_START + 14)

//#define raBID_PNL_RELIEF 2

class raGamePanel;

class raBid: public wxPanel
{
private:
	DECLARE_EVENT_TABLE()

	// Disallow copy constructor/assignment operators
	raBid(final raBid &);
    raBid & operator=(final raBid &);

	int m_min_bid;

	wxPanel *m_main_panel;
	wxGridSizer *m_main_sizer;
	wxBoxSizer *m_main_panel_sizer;
	wxPanel *m_head_panel;
	wxGridSizer *m_head_panel_sizer;
	wxStaticText *m_head_panel_text;
	wxFont m_bold_font;
	wxPanel *m_bidbtn_panel;
	wxGridSizer *m_bidbtn_panel_sizer;
	wxButton *m_buttons[raBID_TOTAL_BTNS];

	wxPanel *m_btns_panel;
	wxGridSizer *m_btns_panel_sizer;
	wxButton *m_button_all;
	wxButton *m_button_pass;

	raGamePanel *m_game;

	void OnButtonClick(wxCommandEvent &event);
public:
	raBid(final wxWindow* parent);
	~raBid();
	boolean SetGamePanel(raGamePanel *game_panel);
	boolean SetPassable(boolean passable = true);
	boolean SetMinimumBid(int min_bid);
};

//#endif


//




//




//



//#include "ra/rabid.h"
//#include "ra/ragamepanel.h"

BEGIN_EVENT_TABLE(raBid, wxPanel)
	EVT_BUTTON(raBID_BTN_ID_ALL, raBid.OnButtonClick)
	EVT_BUTTON(raBID_BTN_ID_PASS, raBid.OnButtonClick)
END_EVENT_TABLE()

raBid.raBid(final wxWindow* parent): wxPanel((wxWindow*)parent)
{
	int i = 0;
	int j = 0;
	int best_width = 0;
	int temp_width = 0;
	int temp_height = 0;

	m_game = null;
	m_min_bid = 0;

	// Initializing the value of all the buttons to null
	for(i = 0; i < raBID_TOTAL_BTNS; ++i)
		m_buttons[i] = null;

	m_button_all = null;
	m_button_pass = null;

	// Calculate the best width for the buttons
	// The best width should be able to contain all bids from 14
	// to 28 and the strings "All" and "Pass"

	best_width = 0;
	for(i = 0 ; i < raBID_TOTAL_BTNS; i++)
	{
		this.GetTextExtent(String.Format(("%d"), i + 14),
			&temp_width, &temp_height);
		best_width = std.max(best_width, temp_width);
	}

	this.GetTextExtent(("All"), &temp_width, &temp_height);
	best_width = std.max(best_width, temp_width);
	this.GetTextExtent(("Pass"), &temp_width, &temp_height);
	best_width = std.max(best_width, temp_width);

	wxLogDebug(String.Format(("Best width %d"), best_width));

//#ifdef __WXMSW__
	this.SetWindowStyle(wxRAISED_BORDER);
//#endif

	m_main_panel = new wxPanel(this);
	if(!m_main_panel)
	{
		wxLogError(String.Format(("Failed to create main panel. %s:%d"), (__FILE__), __LINE__));
		return;
	}

//#ifdef __WXMSW__
	m_main_panel.SetWindowStyle(wxSUNKEN_BORDER);
//#endif

	m_main_panel.SetBackgroundColour(*wxWHITE);
	m_main_sizer = new wxGridSizer(0, 0, 0, 0);


	// TODO : Add error checks
	m_main_panel_sizer = new wxBoxSizer(wxVERTICAL);

	m_head_panel = new wxPanel(m_main_panel);
	//m_head_panel.SetWindowStyle(wxRAISED_BORDER);
	m_head_panel.SetBackgroundColour(raCLR_HEAD_DARK);
	m_head_panel.SetForegroundColour(*wxWHITE);

	m_bold_font = m_head_panel.GetFont();
	m_bold_font.SetWeight(wxFONTWEIGHT_BOLD);
	m_head_panel.SetFont(m_bold_font);

	m_head_panel_sizer = new wxGridSizer(0, 0, 0, 0);
	m_head_panel_text = new wxStaticText(m_head_panel, -1, ("Enter Bid"));
	m_head_panel_sizer.Add(m_head_panel_text, 0,
		wxALIGN_CENTER_HORIZONTAL| wxALIGN_CENTER_VERTICAL|wxALL, 2);

	m_head_panel.SetSizer(m_head_panel_sizer);

	m_bidbtn_panel = new wxPanel(m_main_panel);
	//m_bidbtn_panel.SetWindowStyle(wxSUNKEN_BORDER);
	m_bidbtn_panel_sizer = new wxGridSizer(5, 3, 0, 0);

	for(i = 0; i < raBID_BTN_ROWS; i++)
	{
		for(j = 0; j < raBID_BTN_COLS; j++)
		{
			m_buttons[(i * raBID_BTN_COLS) + j] = new wxButton(m_bidbtn_panel,
				raBID_BTN_ID_START + (i * raBID_BTN_COLS) + j,
				String.Format(("%d"), (i * raBID_BTN_COLS) + j + 14),
				wxDefaultPosition, wxSize(best_width, -1));
			m_bidbtn_panel_sizer.Add(m_buttons[(i * raBID_BTN_COLS) + j], 0,
				wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL|wxEXPAND|wxALL, 1);
			//m_buttons[(i * raBID_BTN_COLS) + j].SetSize(10, 20);
			//m_buttons[(i * raBID_BTN_COLS) + j].SetWindowStyle(wxNO_BORDER);
			m_buttons[(i * raBID_BTN_COLS) + j].SetEventHandler(this.GetEventHandler());
		}
	}

	m_bidbtn_panel.SetSizer(m_bidbtn_panel_sizer);
	m_bidbtn_panel_sizer.Fit(m_bidbtn_panel);

	// Create panel, assosiated sizer to hold the buttons
	// to bid "All" and "Pass"

	m_btns_panel = new wxPanel(m_main_panel);
	m_btns_panel_sizer = new wxGridSizer(1, 2, 0, 0);

	m_button_all = new wxButton(m_btns_panel, raBID_BTN_ID_ALL, ("All"));
	m_button_pass = new wxButton(m_btns_panel, raBID_BTN_ID_PASS, ("Pass"));

	//m_button_all.Disable();

	//m_button_all.SetWindowStyle(wxNO_BORDER);
	//m_button_pass.SetWindowStyle(wxNO_BORDER);

	m_button_all.SetEventHandler(this.GetEventHandler());
	m_button_pass.SetEventHandler(this.GetEventHandler());

	m_btns_panel_sizer.Add(m_button_all, 0,
		wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL|wxEXPAND|wxALL, 1);
	m_btns_panel_sizer.Add(m_button_pass, 0,
		wxALIGN_CENTER_HORIZONTAL|wxALIGN_CENTER_VERTICAL|wxEXPAND|wxALL, 1);

	m_btns_panel.SetSizer(m_btns_panel_sizer);
	m_btns_panel_sizer.Fit(m_btns_panel);

	m_main_panel_sizer.Add(m_head_panel, 0, wxEXPAND | wxALL, raBID_PNL_RELIEF);
	m_main_panel_sizer.Add(m_bidbtn_panel, 0, wxEXPAND, 0);
//#ifdef __WXMSW__
	m_main_panel_sizer.Add(m_btns_panel, 0, wxEXPAND | wxBOTTOM, raBID_PNL_RELIEF * 3);
#else
	m_main_panel_sizer.Add(m_btns_panel, 0, wxEXPAND, 0);
//#endif

	m_main_panel.SetSizer(m_main_panel_sizer);
	m_main_panel_sizer.Fit(m_main_panel);

	m_main_sizer.Add(m_main_panel, 0, wxEXPAND, 0);

	this.SetSizer(m_main_sizer);
	m_main_sizer.Fit(this);

	this.GetEventHandler().Connect(raBID_BTN_ID_START, raBID_BTN_ID_START + raBID_TOTAL_BTNS - 1,
		wxEVT_COMMAND_BUTTON_CLICKED,wxCommandEventHandler(raBid.OnButtonClick));
}

raBid.~raBid()
{
}

//
// Public method/s
//
boolean raBid.SetGamePanel(raGamePanel *game_panel)
{
	m_game = game_panel;
	return true;
}

boolean raBid.SetPassable(boolean passable)
{
	// Enable/disable the pass button
	// as per the input criteria

	m_button_pass.Enable(passable);

	return true;
}

boolean raBid.SetMinimumBid(int min_bid)
{
	int i;

	m_min_bid = min_bid;

	// Disable all the bid buttons less than the minimum bid
	// and enable the rest

	for(i = 0; i < raBID_TOTAL_BTNS; i++)
		m_buttons[i].Enable(!(i < (min_bid - 14)));

	return true;
}

//
// Private method/s
//
void raBid.OnButtonClick(wxCommandEvent &event)
{
	raBidEvent new_event;
	String msg;
	int id;

	if(m_game)
	{
		id = event.GetId();
		switch(id)
		{
		case raBID_BTN_ID_ALL:
			// Alert the user if the bid is for All Cards
			msg.Append(("You have made a bid for All Cards\n\n"));
			msg.Append(("If you want to continue with the bid, click Yes\n"));
			msg.Append(("If you want to cancel the bid and make a new one, click No"));
			if(wxMessageBox(msg, ("Confirm"), wxYES_NO | wxICON_QUESTION) != wxYES)
			{
				event.Skip();
				return;
			}

			new_event.SetBid(gmBID_ALL);
			break;
		case raBID_BTN_ID_PASS:
			new_event.SetBid(gmBID_PASS);
			break;
		default:
			// Alert the user if the bid is relatively high
			// as compared to the minimum possible
			if((raGetBidFromId(id)) >= (m_min_bid + 3))
			{
				msg.Append(String.Format(("You have bid %d\n\n"), raGetBidFromId(id)));
				msg.Append(("If you want to continue with the bid, click Yes\n"));
				msg.Append(("If you want to cancel the bid and make a new one, click No"));

				if(wxMessageBox(msg, ("Confirm"), wxYES_NO | wxICON_QUESTION) != wxYES)
				{
					event.Skip();
					return;
				}
			}
			new_event.SetBid(raGetBidFromId(id));
			break;
		}
		m_game.AddPendingEvent(new_event);
	}
	else
		wxLogError(String.Format(("Game panel not set in raBid. %s:%d"), (__FILE__), __LINE__));

	event.Skip();
}
