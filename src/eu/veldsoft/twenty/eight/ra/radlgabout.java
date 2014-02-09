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
