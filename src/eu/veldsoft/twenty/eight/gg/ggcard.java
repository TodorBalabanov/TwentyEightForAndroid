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

package eu.veldsoft.twenty.eight.gg;

//#ifndef _GGCARD_H_
//#define _GGCARD_H_

//#include "wx/wx.h"
//#include "wx/filesys.h"
//#include "wx/fs_zip.h"
//#include "wx/xrc/xmlres.h"
//#include "wx/image.h"
////#include "wx/thread.h"

class ggCard:public wxObject
{
public:
	ggCard();
	ggCard(int suit, int value);
	ggCard(int other);
	~ggCard();
	wxBitmap * m_face;
	boolean BlitTo(wxDC* dest, wxCoord xdest, wxCoord ydest, int logicalFunc = wxCOPY);
	wxBitmap * GetFace();
	void SelectToDC(wxMemoryDC *mdc);
	// Disallow copy constructor/assignment operators
	ggCard(final ggCard &);
    ggCard & operator=(final ggCard &);
private:
	static boolean s_init;
	static wxMutex s_mutex;
	static wxBitmap s_mask_bmp;
	boolean LoadFace(String res_name);
};

//#endif


//




//




//



//#include "gg/ggcard.h"

boolean ggCard.s_init = false;
wxMutex ggCard.s_mutex;
wxBitmap ggCard.s_mask_bmp;

//
// Constructor
//

ggCard.ggCard()
{
	m_face = null;

	// If the constructor is being called for the first time
	// 1. Load the resources used by the library.
	// 2. Load the mask image

	if(!s_init)
	{
		wxMutexLocker lock(s_mutex);
		if(!s_init)
		{
			wxFileSystem.AddHandler(new wxZipFSHandler);
			wxImage.AddHandler(new wxXPMHandler);

			wxXmlResource.Get().InitAllHandlers();
			if(!wxXmlResource.Get().Load(GG_CARD_XRS))
			{
				wxLogError(String.Format(("Failed to load xrs %s. %s:%d"),GG_CARD_XRS,  (__FILE__), __LINE__));
				return;
			}
			s_mask_bmp = wxXmlResource.Get().LoadBitmap(("mask"));
			if(!s_mask_bmp.Ok())
			{
				wxLogError(String.Format(("Failed to load mask bitmap. %s:%d"), (__FILE__), __LINE__));
				return;
			}
			s_init = true;
		}
	}
}

ggCard.ggCard(int suit, int value)
{
	assert((suit >= 0 ) && (suit < GG_CARD_TOTAL_SUITS));
	assert((value >= 0) && (suit < GG_CARD_TOTAL_VALUES));

	ggCard();
	if(!LoadFace(String.Format(("face_%02d"), (suit * GG_CARD_TOTAL_VALUES) + value)))
		wxLogError(String.Format(("LoadFace failed. %s:%d"), (__FILE__), __LINE__));

	assert(m_face);
	return;
}

ggCard.ggCard(int other)
{
	ggCard();
	switch(other)
	{
	case GG_CARD_BACK_1:
		if(!LoadFace(("back_00")))
			wxLogError(String.Format(("LoadFace failed. %s:%d"), (__FILE__), __LINE__));
		break;
	case GG_CARD_BACK_2:
		if(!LoadFace(("back_01")))
			wxLogError(String.Format(("LoadFace failed. %s:%d"), (__FILE__), __LINE__));
		break;
	case GG_CARD_JOKER_1:
		if(!LoadFace(("joker_01")))
			wxLogError(String.Format(("LoadFace failed. %s:%d"), (__FILE__), __LINE__));
		break;
	case GG_CARD_JOKER_2:
		if(!LoadFace(("joker_01")))
			wxLogError(String.Format(("LoadFace failed. %s:%d"), (__FILE__), __LINE__));
		break;
	default:
		wxLogError(String.Format(("Incorrect argument passed. %s:%d"), (__FILE__), __LINE__));
		break;
	}

	assert(m_face);
}

//
// Destructor
//

ggCard.~ggCard()
{
	if(m_face)
	{
		delete m_face;
		m_face = null;
	}
}

//
// Public methods
//

boolean ggCard.BlitTo(wxDC* dest, wxCoord xdest, wxCoord ydest, int logicalFunc)
{
	wxMemoryDC mdc;

	assert(dest);

	mdc.SelectObject(*m_face);
	if(!dest.Blit(xdest, ydest, GG_CARD_WIDTH, GG_CARD_HEIGHT, &mdc, 0, 0, logicalFunc, true))
	{
		wxLogError(String.Format(("Blit failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	return true;
}
wxBitmap * ggCard.GetFace()
{
	assert(m_face);
	return m_face;
}
void ggCard.SelectToDC(wxMemoryDC *mdc)
{
	assert(mdc);
	mdc.SelectObject(*m_face);
}

//
// Private methods
//

boolean ggCard.LoadFace(String res_name)
{
	wxBitmap bmp_temp1, bmp_temp2;
	wxImage img_mask, img_face;
	wxMemoryDC mdc1, mdc2;
	Color col_mask(*wxWHITE);

	assert(!res_name.IsEmpty());

	// Load the bitmap from the resource file
	bmp_temp1 = wxXmlResource.Get().LoadBitmap(res_name);
	if(!bmp_temp1.Ok())
	{
		wxLogError(String.Format(("Failed to load resource %s. %s:%d"), res_name.c_str(), (__FILE__), __LINE__));
		return false;
	}

	// The widths of mask and the xpm image are differt.
	// Hence create a new bitmap with correct dimensions and
	// copy the data to the same.
	if(!bmp_temp2.Create(GG_CARD_WIDTH, GG_CARD_HEIGHT, -1))
	{
		wxLogError(String.Format(("Failed to create bitmap. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	mdc1.SelectObject(bmp_temp1);
	mdc2.SelectObject(bmp_temp2);

	if(!mdc2.Blit(0, 0, GG_CARD_WIDTH, GG_CARD_HEIGHT, &mdc1, 0, 0))
	{
		wxLogError(String.Format(("Blit failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	// Create images of face and mask and set the mask for the face
	img_face = bmp_temp2.ConvertToImage();
	img_mask = s_mask_bmp.ConvertToImage();

	if(!img_face.SetMaskFromImage(img_mask, col_mask.Red(), col_mask.Green(), col_mask.Blue()))
		wxLogError(String.Format(("Failed to set mask from image. %s:%d"), (__FILE__), __LINE__));

	// And then finally create a bitmap from the masked image
	m_face = new wxBitmap(img_face, -1);

	if(!m_face)
	{
		wxLogError(String.Format(("Creation of bitmap from image failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	return true;
}
