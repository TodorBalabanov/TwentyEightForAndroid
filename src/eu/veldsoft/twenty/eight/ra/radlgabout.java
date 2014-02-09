

//




//




//



package eu.veldsoft.twenty.eight.ra;

//#ifndef _RADLGABOUT_H_
//#define _RADLGABOUT_H_

////#if defined(__GNUG__) && !defined(NO_GCC_PRAGMA)
////#pragma interface "radlgabout.h"
////#endif

//#include "wx/xrc/xmlres.h"

class raDlgAbout: public wxDialog
{
    DECLARE_DYNAMIC_CLASS( raDlgAbout )
    DECLARE_EVENT_TABLE()
private:
	// Disallow copy constructor/assignment operators
	raDlgAbout(final raDlgAbout &);
    raDlgAbout & operator=(final raDlgAbout &);
public:
    raDlgAbout( );
    raDlgAbout( wxWindow* parent, wxWindowID id , final String& caption, final wxPoint& pos, final wxSize& size, long style);
    void OnAboutBtnOkClick( wxCommandEvent& event );
};

//#endif


//




//




//



//#ifdef __BORLANDC__
//#pragma hdrstop
//#endif

//#ifndef WX_PRECOMP
//#include "wx/wx.h"
//#endif

//#include "ra/radlgabout.h"
//#include "ra/racommon.h"

IMPLEMENT_DYNAMIC_CLASS( raDlgAbout, wxDialog )

// Event table
BEGIN_EVENT_TABLE( raDlgAbout, wxDialog )
    EVT_BUTTON( XRCID("m_radlgabout_ok"), raDlgAbout.OnAboutBtnOkClick )
END_EVENT_TABLE()


raDlgAbout.raDlgAbout( )
{
}

raDlgAbout.raDlgAbout( wxWindow* parent, wxWindowID id, final String& caption, final wxPoint& pos, final wxSize& size, long style )
{
	SetParent(parent);
	if (!wxXmlResource.Get().LoadDialog(this, GetParent(), _T("raDlgAbout")))
		wxLogError(("Missing wxXmlResource.Get().Load() in OnInit()?"));
}

void raDlgAbout.OnAboutBtnOkClick( wxCommandEvent& event )
{
    Destroy();
}
