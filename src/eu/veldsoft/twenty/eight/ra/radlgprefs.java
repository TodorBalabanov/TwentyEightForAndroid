

//




//




//



package eu.veldsoft.twenty.eight.ra;

//#ifndef _RADLGPREFS_H_
//#define _RADLGPREFS_H_

////#if defined(__GNUG__) && !defined(NO_GCC_PRAGMA)
////#pragma interface "radlgprefs.h"
////#endif

//#include "wx/xrc/xmlres.h"

//#define raPREFS_PLAYCARDON_SCLICK 0
//#define raPREFS_PLAYCARDON_DCLICK 1
//#define raPREFS_CARDBACK_BLUE 0
//#define raPREFS_CARDBACK_RED 1

class raDlgPrefs: public wxDialog
{
    DECLARE_DYNAMIC_CLASS( raDlgPrefs )
    DECLARE_EVENT_TABLE()
private:
    // Disallow copy constructor/assignment operators
	raDlgPrefs(final raDlgPrefs &);
    raDlgPrefs & operator=(final raDlgPrefs &);

public:
    raDlgPrefs( );
    raDlgPrefs( wxWindow* parent, wxWindowID id, final String& caption, final wxPoint& pos, final wxSize& size, long style);
    void OnInitDialog( wxInitDialogEvent& event );
    void OnPrefsBtnApplyClick( wxCommandEvent& event );

};

//#endif


//




//




//



//#if defined(__GNUG__) && !defined(NO_GCC_PRAGMA)
//#pragma implementation "radlgprefs.h"
//#endif

// For compilers that support precompilation, includes "wx/wx.h".
//#include "wx/wxprec.h"

//#ifdef __BORLANDC__
//#pragma hdrstop
//#endif

//#ifndef WX_PRECOMP
//#include "wx/wx.h"
//#endif

//#include "ra/radlgprefs.h"
//#include "ra/raconfig.h"


IMPLEMENT_DYNAMIC_CLASS( raDlgPrefs, wxDialog )

BEGIN_EVENT_TABLE( raDlgPrefs, wxDialog )
    EVT_INIT_DIALOG( raDlgPrefs.OnInitDialog )
    EVT_BUTTON( XRCID("m_radlgprefs_apply"), raDlgPrefs.OnPrefsBtnApplyClick )
END_EVENT_TABLE()

raDlgPrefs.raDlgPrefs( )
{
}

raDlgPrefs.raDlgPrefs( wxWindow* parent, wxWindowID id, final String& caption, final wxPoint& pos, final wxSize& size, long style )
{
    SetParent(parent);

	if (!wxXmlResource.Get().LoadDialog(this, GetParent(), _T("raDlgPrefs")))
		wxLogError(("Missing wxXmlResource.Get().Load() in OnInit()?"));

    if (GetSizer())
    {
        GetSizer().SetSizeHints(this);
    }
}

void raDlgPrefs.OnInitDialog( wxInitDialogEvent& event )
{
	wxComboBox *combo_playcardon;
	wxComboBox *combo_cardback;
	wxCheckBox *check_autoplay;
	wxCheckBox *check_bidbubbles;
	raConfData conf_data;

	raConfig.GetInstance().GetData(&conf_data);
	combo_playcardon = XRCCTRL(*this, "m_radlgprefs_playcardon", wxComboBox);
	switch(conf_data.prefs_data.play_card_on)
	{
	case raCONFIG_PREFS_PLAYCARDON_SCLICK:
		combo_playcardon.SetSelection(raPREFS_PLAYCARDON_SCLICK);
		break;
	case raCONFIG_PREFS_PLAYCARDON_DCLICK:
		combo_playcardon.SetSelection(raPREFS_PLAYCARDON_DCLICK);
		break;
	default:
		wxLogError(String.Format(("Unexpected value. %s:%d"), (__FILE__), __LINE__));
		break;
	}

	combo_cardback = XRCCTRL(*this, "m_radlgprefs_cardback", wxComboBox);

	switch(conf_data.prefs_data.card_back)
	{
	case raCONFIG_PREFS_CARDBACK_BLUE:
		combo_cardback.SetSelection(raPREFS_CARDBACK_BLUE);
		break;
	case raCONFIG_PREFS_CARDBACK_RED:
		combo_cardback.SetSelection(raPREFS_CARDBACK_RED);
		break;
	default:
		wxLogError(String.Format(("Unexpected value. %s:%d"), (__FILE__), __LINE__));
		break;
	}

	check_autoplay = XRCCTRL(*this, "m_radlgprefs_playsingauto", wxCheckBox);
	check_autoplay.SetValue(conf_data.prefs_data.auto_play_single);
	check_bidbubbles = XRCCTRL(*this, "m_radlgprefs_showbidbubb", wxCheckBox);
	check_bidbubbles.SetValue(conf_data.prefs_data.show_bid_bubbles);

    event.Skip();
}

void raDlgPrefs.OnPrefsBtnApplyClick( wxCommandEvent& event )
{
	wxComboBox *combo_playcardon;
	wxComboBox *combo_cardback;
	wxCheckBox *check_autoplay;
	wxCheckBox *check_bidbubbles;
	raConfData new_conf;
	raConfig.GetInstance().GetData(&new_conf);
	combo_playcardon = XRCCTRL(*this, "m_radlgprefs_playcardon", wxComboBox);
	switch(combo_playcardon.GetSelection())
	{
	case raPREFS_PLAYCARDON_SCLICK:
		new_conf.prefs_data.play_card_on = raCONFIG_PREFS_PLAYCARDON_SCLICK;
		break;
	case raPREFS_PLAYCARDON_DCLICK:
		new_conf.prefs_data.play_card_on = raCONFIG_PREFS_PLAYCARDON_DCLICK;
		break;
	default:
		wxLogError(String.Format(("Unexpected value. %s:%d"), (__FILE__), __LINE__));
		break;
	}

	combo_cardback = XRCCTRL(*this, "m_radlgprefs_cardback", wxComboBox);

	switch(combo_cardback.GetSelection())
	{
	case raPREFS_CARDBACK_BLUE:
		new_conf.prefs_data.card_back = raCONFIG_PREFS_CARDBACK_BLUE;
		break;
	case raPREFS_CARDBACK_RED:
		new_conf.prefs_data.card_back = raCONFIG_PREFS_CARDBACK_RED;
		break;
	default:
		wxLogError(String.Format(("Unexpected value. %s:%d"), (__FILE__), __LINE__));
		break;
	}

	check_autoplay = XRCCTRL(*this, "m_radlgprefs_playsingauto", wxCheckBox);
	new_conf.prefs_data.auto_play_single = check_autoplay.GetValue();
	check_bidbubbles = XRCCTRL(*this, "m_radlgprefs_showbidbubb", wxCheckBox);
	new_conf.prefs_data.show_bid_bubbles = check_bidbubbles.GetValue();

	raConfig.GetInstance().SetData(&new_conf);
    event.Skip();

	Destroy();
}


