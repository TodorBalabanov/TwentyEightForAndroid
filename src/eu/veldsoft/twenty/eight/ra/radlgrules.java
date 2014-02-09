

//




//




//



package eu.veldsoft.twenty.eight.ra;

//#ifndef _RADLGRULES_H_
//#define _RADLGRULES_H_

////#if defined(__GNUG__) && !defined(NO_GCC_PRAGMA)
////#pragma interface "radlgrules.h"
////#endif

//#include "wx/xrc/xmlres.h"

class raDlgRules: public wxDialog
{
    DECLARE_DYNAMIC_CLASS( raDlgRules )
    DECLARE_EVENT_TABLE()
private:
	// Disallow copy constructor/assignment operators
	raDlgRules(final raDlgRules &);
    raDlgRules & operator=(final raDlgRules &);
public:
    raDlgRules( );
    raDlgRules( wxWindow* parent, wxWindowID id, final String& caption, final wxPoint& pos, final wxSize& size, long style);
    void OnInitDialog( wxInitDialogEvent& event );
    void OnRulesBtnApplyClick( wxCommandEvent& event );
};

//#endif


//




//




//



//#if defined(__GNUG__) && !defined(NO_GCC_PRAGMA)
//#pragma implementation "radlgrules.h"
//#endif

// For compilers that support precompilation, includes "wx/wx.h".
//#include "wx/wxprec.h"

//#ifdef __BORLANDC__
//#pragma hdrstop
//#endif

//#ifndef WX_PRECOMP
//#include "wx/wx.h"
//#endif

//#include "ra/radlgrules.h"
//#include "ra/raconfig.h"

IMPLEMENT_DYNAMIC_CLASS( raDlgRules, wxDialog )

BEGIN_EVENT_TABLE( raDlgRules, wxDialog )
    EVT_INIT_DIALOG( raDlgRules.OnInitDialog )
    EVT_BUTTON( XRCID("m_radlgrules_apply"), raDlgRules.OnRulesBtnApplyClick )
END_EVENT_TABLE()

raDlgRules.raDlgRules( )
{
}

raDlgRules.raDlgRules( wxWindow* parent, wxWindowID id, final String& caption, final wxPoint& pos, final wxSize& size, long style )
{
	SetParent(parent);
    if (!wxXmlResource.Get().LoadDialog(this, GetParent(), _T("raDlgRules")))
        wxLogError(("Missing wxXmlResource.Get().Load() in OnInit()?"));
	if (GetSizer())
	{
		GetSizer().SetSizeHints(this);
	}
}


void raDlgRules.OnRulesBtnApplyClick( wxCommandEvent& event )
{
	wxRadioButton *radio_clockwise;
	wxRadioButton *radio_anticlockwise;
	wxComboBox *combo_minbid3;
	wxCheckBox *check_waiverule4;
	wxCheckBox *check_sluffjacks;
	raConfData new_conf;

	raConfig.GetInstance().GetData(&new_conf);

	radio_clockwise = XRCCTRL(*this, "m_radlgrules_clockwise", wxRadioButton);
	radio_anticlockwise = XRCCTRL(*this, "m_radlgrules_anticlockwise", wxRadioButton);
	combo_minbid3 = XRCCTRL(*this, "m_radlgrules_minbidthird", wxComboBox);
	check_waiverule4 = XRCCTRL(*this, "m_radlgrules_waiverule4", wxCheckBox);
	check_sluffjacks = XRCCTRL(*this, "m_radlgrules_sluffjacks", wxCheckBox);

	if(radio_clockwise.GetValue())
	{
		new_conf.game_data.clockwise = true;
	}
	else if(radio_anticlockwise.GetValue())
	{
		new_conf.game_data.clockwise = false;
	}
	else
	{
		wxLogError(String.Format(("Unexpected value. %s:%d"), (__FILE__), __LINE__));
	}

	switch(combo_minbid3.GetSelection())
	{
	case 0:
		new_conf.game_data.min_bid3 = 23;
		break;
	case 1:
		new_conf.game_data.min_bid3 = 24;
		break;
	default:
		wxLogError(String.Format(("Unexpected value. %s:%d"), (__FILE__), __LINE__));
		break;
	}

	// Check waiver of rule 4
	new_conf.game_data.waive_rule4 = !check_waiverule4.GetValue();
	new_conf.game_data.sluff_jacks = check_sluffjacks.GetValue();


	raConfig.GetInstance().SetData(&new_conf);
    event.Skip();

	Destroy();
}

void raDlgRules.OnInitDialog( wxInitDialogEvent& event )
{
	wxRadioButton *radio_clockwise;
	wxRadioButton *radio_anticlockwise;
	wxComboBox *combo_minbid3;
	wxCheckBox *check_waiverule4;
	wxCheckBox *check_sluffjacks;
	raConfData conf_data;

	raConfig.GetInstance().GetData(&conf_data);
	radio_clockwise = XRCCTRL(*this, "m_radlgrules_clockwise", wxRadioButton);
	radio_anticlockwise = XRCCTRL(*this, "m_radlgrules_anticlockwise", wxRadioButton);
	combo_minbid3 = XRCCTRL(*this, "m_radlgrules_minbidthird", wxComboBox);
	check_waiverule4 = XRCCTRL(*this, "m_radlgrules_waiverule4", wxCheckBox);
	check_sluffjacks = XRCCTRL(*this, "m_radlgrules_sluffjacks", wxCheckBox);

	if(conf_data.game_data.clockwise)
	{
		radio_clockwise.SetValue(true);
	}
	else
	{
		radio_anticlockwise.SetValue(true);
	}

	assert((conf_data.game_data.min_bid3 == 23) || (conf_data.game_data.min_bid3 == 24));
	switch(conf_data.game_data.min_bid3)
	{
	case 23:
		combo_minbid3.SetSelection(0);
		break;
	case 24:
		combo_minbid3.SetSelection(1);
		break;
	default:
		wxLogError(String.Format(("Unexpected value. %s:%d"), (__FILE__), __LINE__));
		break;
	}

	check_waiverule4.SetValue(!conf_data.game_data.waive_rule4);
	check_sluffjacks.SetValue(conf_data.game_data.sluff_jacks);


    event.Skip();
}


