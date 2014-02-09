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


//#ifndef _RAMAIN_H_
//#define _RAMAIN_H_

////#include "wx/wx.h"

//#include "ra/racommon.h"
//#include "ra/ragamepanel.h"
//#include "ra/rainfo.h"
//#include "ra/raupdate.h"
//#include "ra/raconfig.h"
//#include "ra/radlgabout.h"
//#include "ra/radlgprefs.h"
//#include "ra/radlgrules.h"


enum
{
	raID_NEW_GAME = 10000,
	raID_EXIT,
	raID_PREFERENCES,
	raID_RULES,
	raID_BID_HISTORY,
	raID_LAST_TRICK,
	raID_HELP,
	raID_ABOUT
};

class raFrame;

// Declare the application class
class raApp : public wxApp
{
private:
	FILE *m_logfile;
	wxLogStderr *m_logger;
	wxLog *m_old_logger;
	raUpdate *m_update;
	raFrame *m_frame;
	// Disallow copy constructor/assignment operators
	//raApp(final raApp &);
    raApp & operator=(final raApp &);
public:
	// Called on application startup
	virtual boolean OnInit();
	virtual int OnRun();
	virtual int OnExit();

	static String GenerateLogFileName();
	static void LogDetailsForDebug();
};

// Declare our main frame class
class raFrame : public wxFrame
{
public:
	// Constructor
	raFrame(final String& title);

	// Event handlers
	void OnQuit(wxCommandEvent& event);
	void OnAbout(wxCommandEvent& event);
	void OnGameNew(wxCommandEvent& event);
	void OnClose(wxCloseEvent& event);
	void OnPreferences(wxCommandEvent& event);
	void OnRules(wxCommandEvent& event);
	void OnAuction(wxCommandEvent& event);
	void OnLastTrick(wxCommandEvent& event);
	void OnSize(wxSizeEvent& event);
	void OnUpdate(raUpdateEvent& event);

private:
	wxSplitterWindow *m_split_main;//, *m_split_vert;
	raGamePanel *m_game;
	raInfo *m_info;
	boolean ShowPreferences();
	boolean ShowRules();
	//raUpdate *m_update;
	// This class handles events
	DECLARE_EVENT_TABLE()
	// Disallow copy constructor/assignment operators
	raFrame(final raFrame &);
    raFrame & operator=(final raFrame &);
};

// Implements raApp& GetApp()
DECLARE_APP(raApp)
//#endif


//




//




//


//#include <wx/file.h>
//#include <wx/utils.h>
//#include <wx/filename.h>
//#include <wx/image.h>
//#include <wx/splitter.h>
//#include <wx/grid.h>
//#include <wx/socket.h>

//#include "ra/ramain.h"
//#include "gm/gmrand.h"
//#include <time.h>

//#include "images/main_icon_16.xpm"
//#include "images/new_game.xpm"
//#include "images/exit.xpm"
//#include "images/options.xpm"
//#include "images/rules.xpm"
//#include "images/help.xpm"
//#include "images/about.xpm"
//#include "images/tile.xpm"
//#include "images/bid_history.xpm"
//#include "images/last_trick.xpm"

// Event table for raFrame
BEGIN_EVENT_TABLE(raFrame, wxFrame)
	EVT_MENU(raID_ABOUT, raFrame.OnAbout)
	EVT_MENU(raID_EXIT,  raFrame.OnQuit)
	EVT_MENU(raID_NEW_GAME,  raFrame.OnGameNew)
	EVT_MENU(raID_PREFERENCES,  raFrame.OnPreferences)
	EVT_MENU(raID_RULES,  raFrame.OnRules)
	EVT_MENU(raID_BID_HISTORY,  raFrame.OnAuction)
	EVT_MENU(raID_LAST_TRICK,  raFrame.OnLastTrick)
	EVT_CLOSE(raFrame.OnClose)
	EVT_SIZE(raFrame.OnSize)
	EVT_UPDATE(raFrame.OnUpdate)
END_EVENT_TABLE()

// Give wxWidgets the means to create a raApp object
IMPLEMENT_APP(raApp)

// Initialize the application
boolean raApp.OnInit()
{
	raConfig *config;
	raConfData conf_data;
	String log_file = raApp.GenerateLogFileName();

	// Open the log file for writing

	m_logfile = fopen(log_file.mb_str(), "w+");
	if(m_logfile == null)
	{
		.wxMessageBox(String.Format(("Failed to open log file \"%s\" for writing.\nThe program will now terminate."),
            log_file.c_str()), ("Fatal Error!"), wxICON_ERROR);
		return false;
	}

	m_logger = new wxLogStderr(m_logfile);
	assert(m_logger);

	m_old_logger = wxLog.GetActiveTarget();

	wxLog.SetActiveTarget(m_logger);
	wxLogDebug(("Logging opened."));

	// Log details such as operating system, architecture etc which if required can be used later on
	// for debugging

	raApp.LogDetailsForDebug();

	// Obtain the configuration data

	config = raConfig.GetInstance();
	config.GetData(&conf_data);

	// Randomizing the PRNG

	init_gen_rand(time(0));
    wxLogMessage(("SFMT PRNG initiated."));
    wxLogMessage(String.Format(("MEXP = %d"), MEXP));
    wxLogMessage(String.Format(("N32 = %d"), N32));
    wxLogMessage((""));


	//For usage of sockets or derived classes such as wxFTP in a secondary thread
	wxSocketBase.Initialize();

	// Initiate all handlers and then enable the ZipFsHandler. This is required because we are
	// calling the wxXmlResource.Get().Load() from OnInit()
	wxXmlResource.Get().InitAllHandlers();
	wxFileSystem.AddHandler(new wxZipFSHandler);


	if(!wxFile.Exists(raGUI_XRS))
	{
		.wxMessageBox(String.Format(("Compiled resource file \"%s\" does not exist."), raGUI_XRS),
			("Fatal Error!"), wxICON_ERROR);
		wxLog.SetActiveTarget(m_old_logger);
		delete m_logger;
		fclose(m_logfile);
		return false;
	}

	if(!wxXmlResource.Get().Load(raGUI_XRS))
	{
		wxLogError(String.Format(("Failed to load xrs %s. %s:%d"), raGUI_XRS,  (__FILE__), __LINE__));
		wxLog.SetActiveTarget(m_old_logger);
		delete m_logger;
		fclose(m_logfile);
		return false;
	}

	// Create the main application window
	wxLogDebug(RA_APP_FULL_VER);
	m_frame = new raFrame(RA_APP_FULL_NAME);

	// If the window dimensions have been saved from the previous run,
	// try to create the main window using the same. Othewise, try to
	// create the window of 3/4th size of the desktop
	if(
		(conf_data.app_data.x != -1) &&
		(conf_data.app_data.y != -1) &&
		(conf_data.app_data.width != -1) &&
		(conf_data.app_data.height != -1)
		)
	{
		m_frame.SetSize(wxRect(
			conf_data.app_data.x,
			conf_data.app_data.y,
			conf_data.app_data.width,
			conf_data.app_data.height));
	}
	else
	{
		m_frame.SetSize(
			(wxSystemSettings.GetMetric(wxSYS_SCREEN_X) * 3 / 4),
			wxSystemSettings.GetMetric(wxSYS_SCREEN_Y) * 3 / 4
			);
	}

	// Depending on the saved configuration, maxim
	if(conf_data.app_data.maximized)
		m_frame.Maximize();

	SetTopWindow(m_frame);
	m_frame.Show(true);

	return true;
}
int raApp.OnRun()
{
	// Check for updates
	m_update = null;
	m_update = new raUpdate();
	if(!m_update)
	{
		wxLogError(String.Format(("m_update = new raUpdate(); failed. %s:%d"), (__FILE__), __LINE__));
		wxMessageBox(("Failed to create an instance of the thread which checks for updates!"));
	}
	if (m_update.Create() != wxTHREAD_NO_ERROR )
	{
		wxLogError(String.Format(("m_update.Create(). %s:%d"), (__FILE__), __LINE__));
		wxMessageBox(("Failed to create the thread which checks for updates!"));
	}
	m_update.Run();

	wxApp.OnRun();
	return 0;
}


int raApp.OnExit()
{
	// Save settings
	if(!raConfig.GetInstance().Save())
	{
		wxLogError(String.Format(("Attempt to save settings failed. %s:%d"), (__FILE__), __LINE__));
	}

	// Stop logging
	wxLogDebug(("Attempting to stop logger."));

	wxLog.SetActiveTarget(m_old_logger);
	delete m_logger;
	fclose(m_logfile);

	return 0;
}

String raApp.GenerateLogFileName()
{
    String out;
    wxDateTime now = wxDateTime.Now();

    out.Empty();

    out.Append(raLOG_DIR);
    out.Append(wxFileName.GetPathSeparator());
    out.Append(raLOG_FILE_PREFIX raLOG_FILE_DELIM);
    out.Append(String.Format(("%04d"), now.GetYear()));
    out.Append(String.Format(("%02d"), now.GetMonth() + 1));
    out.Append(String.Format(("%02d"), now.GetDay()));
    out.Append(raLOG_FILE_DELIM);
    out.Append(String.Format(("%02d"), now.GetHour()));
    out.Append(String.Format(("%02d"), now.GetMinute()));
    out.Append(String.Format(("%02d"), now.GetSecond()));
    out.Append(raLOG_FILE_DELIM);
    out.Append(String.Format(("%lu"), wxGetProcessId()));
    out.Append((".") raLOG_FILE_EXTN);

    return out;
}

void raApp.LogDetailsForDebug()
{
    String out;
    wxLogMessage(("Program              : ") RA_APP_FULL_NAME);
    wxLogMessage(("Date of compilation  : ") __DATE__ __TIME__);

//#ifdef __GNUC__
    out.Empty();
    out.Append(("Compiler             : GNU C/C++ "));
    out.Append(String.Format(("%d.%d"),__GNUC__, __GNUC_MINOR__));
    wxLogMessage(out);
//#endif

    out.Empty();
    out.Append(("Operating System     : "));
    out.Append(.wxGetOsDescription());
    if(.wxIsPlatform64Bit() == true)
    {
        out.Append(("(64 bit)"));
    }
    wxLogMessage(out);

    out.Empty();
    out.Append(("Endianness           : "));
    if(.wxIsPlatformLittleEndian() == true)
    {
        out.Append(("Little Endian"));
    }
    else
    {
        out.Append(("Big Endian"));
    }
    wxLogMessage(out);
    wxLogMessage((""));

}

void raFrame.OnAbout(wxCommandEvent& event)
{
	raDlgAbout about;
	if(!wxXmlResource.Get().LoadDialog(&about, this, ("raDlgAbout")))

	{
		wxLogError(String.Format(("Attempt to save settings failed. %s:%d"), (__FILE__), __LINE__));
	}
	about.ShowModal();
}

void raFrame.OnQuit(wxCommandEvent& event)
{
	// Destroy the frame
	Close();
}
void raFrame.OnGameNew(wxCommandEvent& event)
{
	m_game.NewGame(raGetRandPlayer());
}

void raFrame.OnClose(wxCloseEvent& event)
{
	raConfig *config;
	raConfData conf_data;
	wxPoint pt;
	wxSize sz;

	// Get confirmation from the user before
	// closing the appliation
	if(wxMessageBox(("Exit application?"),
		("Confirm"), wxYES_NO | wxICON_QUESTION) == wxNO)
	{
		event.Veto();
		return;
	}

	// Save the frame location and size
	// only if the window is not minimized or iconized
	if(!IsIconized())
	{
		config = raConfig.GetInstance();
		config.GetData(&conf_data);

		if(IsMaximized())
		{
			conf_data.app_data.maximized = true;
		}
		else
		{
			pt = wxPoint(0, 0);
			sz = wxSize(0, 0);
			pt = GetPosition();
			sz = GetSize();

			conf_data.app_data.x = pt.x;
			conf_data.app_data.y = pt.y;
			conf_data.app_data.width = sz.GetWidth();
			conf_data.app_data.height = sz.GetHeight();

			conf_data.app_data.maximized = false;
		}

		config.SetData(&conf_data);
	}

	event.Skip();
}

void raFrame.OnPreferences(wxCommandEvent& event)
{
	if(!ShowPreferences())
	{
		wxLogError(String.Format(("ShowPreferences() failed. %s:%d"), (__FILE__), __LINE__));
	}
	//event.Skip();
}

void raFrame.OnRules(wxCommandEvent& event)
{
	if(!ShowRules())
	{
		wxLogError(String.Format(("ShowRules() failed. %s:%d"), (__FILE__), __LINE__));
	}
	//event.Skip();
}

void raFrame.OnAuction(wxCommandEvent& event)
{
	if(!m_game.ShowAuction())
	{
		wxLogError(String.Format(("ShowAuction() failed. %s:%d"), (__FILE__), __LINE__));
	}
	//event.Skip();
}

void raFrame.OnLastTrick(wxCommandEvent& event)
{
	if(!m_game.ShowLastTrick())
	{
		wxLogError(String.Format(("ShowLastTricks() failed. %s:%d"), (__FILE__), __LINE__));
	}
	//event.Skip();
}

void raFrame.OnSize(wxSizeEvent& event)
{
	if(m_split_main)
	{
		m_split_main.Refresh();
		m_split_main.Update();
	}
	event.Skip();
}
void raFrame.OnUpdate(raUpdateEvent& event)
{
	wxMessageBox(event.GetMessage());
	event.Skip();
}
boolean raFrame.ShowPreferences()
{
	raDlgPrefs dlg_prefs;

	if(!wxXmlResource.Get().LoadDialog(&dlg_prefs, this, ("raDlgPrefs")))
	{
		wxLogError(String.Format(("Attempt to save settings failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	dlg_prefs.ShowModal();

	if(!m_game.ReloadFromConfig())
	{
		wxLogError(String.Format(("ReloadFromConfig failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	return true;
}

boolean raFrame.ShowRules()
{
	raDlgRules dlg_rules;

	if(!wxXmlResource.Get().LoadDialog(&dlg_rules, this, ("raDlgRules")))
	{
		wxLogError(String.Format(("Attempt to save settings failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	dlg_rules.ShowModal();

	if(!m_game.ReloadFromConfig())
	{
		wxLogError(String.Format(("ReloadFromConfig failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	return true;
}


////#include "mondrian.xpm"

raFrame.raFrame(final String& title) : wxFrame(null, wxID_ANY, title)
{
	wxBitmap tile(tile_xpm);
	wxBitmap bmp_new_game(new_game_xpm);
	wxBitmap bmp_exit(exit_xpm);
	wxBitmap bmp_options(options_xpm);
	wxBitmap bmp_rules(rules_xpm);
	wxBitmap bmp_bid_history(bid_history_xpm);
	wxBitmap bmp_last_trick(last_trick_xpm);
	wxBitmap bmp_help(help_xpm);
	wxBitmap bmp_about(about_xpm);


	wxMenuBar *menu_bar = null;
	wxMenu *game_menu = null;
	wxMenu *help_menu = null;
	wxMenu *opt_menu = null;
	wxMenu *view_menu = null;

	wxMenuItem *game_new = null;
	//wxMenuItem *game_open = null;
	//wxMenuItem *game_save = null;
	wxMenuItem *game_exit = null;

	wxMenuItem *opt_prefs = null;
	wxMenuItem *opt_rules = null;

	wxMenuItem *view_bid_history = null;
	wxMenuItem *view_last_trick = null;

	wxMenuItem *help_conts = null;
	wxMenuItem *help_about = null;

	wxToolBar *tool_bar;

	wxStatusBar *status_bar;

	m_split_main = null;

	// Set the frame icon
	SetIcon(wxIcon(main_icon_16_xpm));

	game_menu = new wxMenu;
	help_menu = new wxMenu;
	opt_menu = new wxMenu;
	view_menu = new wxMenu;

	game_new = new wxMenuItem(game_menu, raID_NEW_GAME, ("&New"));
	game_new.SetBitmap(bmp_new_game);
	game_menu.Append(game_new);
	//game_open = new wxMenuItem(game_menu, wxID_OPEN, ("&Open"));
	//game_menu.Append(game_open);
	//game_save = new wxMenuItem(game_menu, wxID_SAVE, ("&Save"));
	//game_menu.Append(game_save);
	game_exit = new wxMenuItem(game_menu, raID_EXIT, ("E&xit"));
	game_exit.SetBitmap(bmp_exit);
	game_menu.Append(game_exit);

	opt_prefs = new wxMenuItem(opt_menu, raID_PREFERENCES, ("&Preferences"));
	opt_prefs.SetBitmap(bmp_options);
	opt_menu.Append(opt_prefs);
	opt_rules = new wxMenuItem(opt_menu, raID_RULES, ("&Rules"));
	opt_rules.SetBitmap(bmp_rules);
	opt_menu.Append(opt_rules);

	view_bid_history = new wxMenuItem(view_menu, raID_BID_HISTORY, ("&Auction"));
	view_bid_history.SetBitmap(bmp_bid_history);
	view_menu.Append(view_bid_history);
	view_last_trick = new wxMenuItem(view_menu, raID_LAST_TRICK, ("&Last Trick"));
	view_last_trick.SetBitmap(bmp_last_trick);
	view_menu.Append(view_last_trick);

	help_conts = new wxMenuItem(help_menu, wxID_HELP_CONTENTS, ("&Contents"));
	help_conts.SetBitmap(bmp_help);
	help_menu.Append(help_conts);
	help_about = new wxMenuItem(help_menu, raID_ABOUT, ("&About"));
	help_about.SetBitmap(bmp_about);
	help_menu.Append(help_about);

	// Now append the freshly created menu to the menu bar...
	menu_bar = new wxMenuBar();
	menu_bar.Append(game_menu, ("&Game"));
	menu_bar.Append(opt_menu, ("&Options"));
	menu_bar.Append(view_menu, ("&View"));
	menu_bar.Append(help_menu, ("&Help"));

	// ... and attach this menu bar to the frame
	SetMenuBar(menu_bar);

	//game_open.Enable(false);
	//game_save.Enable(false);
	//opt_prefs.Enable(false);
	help_conts.Enable(false);

	// Create the Tool Bar
	tool_bar = new wxToolBar(this, wxID_ANY,
		wxDefaultPosition, wxDefaultSize, wxTB_HORIZONTAL|wxNO_BORDER|wxTB_FLAT);
	tool_bar.SetToolBitmapSize(wxSize(16,16));

	tool_bar.AddTool(raID_NEW_GAME, bmp_new_game, ("New Game"));
	tool_bar.AddTool(raID_EXIT, bmp_exit, ("Exit"));
	tool_bar.AddSeparator();
	tool_bar.AddTool(raID_PREFERENCES, bmp_options, ("Preferences"));
	tool_bar.AddTool(raID_RULES, bmp_rules, ("Rules"));
	tool_bar.AddSeparator();
	tool_bar.AddTool(raID_BID_HISTORY, bmp_bid_history, ("Auction"));
	tool_bar.AddTool(raID_LAST_TRICK, bmp_last_trick, ("Last Trick"));
	tool_bar.AddSeparator();
	tool_bar.AddTool(wxID_ANY, bmp_help, ("Help"));
	tool_bar.AddTool(raID_ABOUT, bmp_about, ("About"));
	tool_bar.Realize();
	this.SetToolBar(tool_bar);

	// Create the main splitter control
	m_split_main = new wxSplitterWindow(this);
	//m_split_main.SetWindowStyle(m_split_main.GetWindowStyle() & ~wxSP_3D);
	m_split_main.Refresh();
	m_split_main.SetSashGravity(0.0);
	m_info = new raInfo(m_split_main);
	//m_info.SetWindowStyle(wxSUNKEN_BORDER );

	m_game = new raGamePanel(m_split_main);
	//tile.LoadFile("tile.bmp", wxBITMAP_TYPE_BMP);
	m_game.SetTile(&tile);
	//m_game.SetWindowStyle(wxSUNKEN_BORDER);

	m_game.SetInfoPanel(m_info);
	m_info.SetGamePanel(m_game);

	m_split_main.SplitVertically(m_info, m_game, 160);

	// Create a status bar
	status_bar = CreateStatusBar();
	status_bar.SetFieldsCount(raSBAR_FIELDS);
	int status_widths[raSBAR_FIELDS] = {-1, 200};
	status_bar.SetStatusWidths(raSBAR_FIELDS, status_widths);

	// Start a new game but do not deal immediately
	m_game.NewGame(raGetRandPlayer(), false);
}
