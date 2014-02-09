

//




//




//



package eu.veldsoft.twenty.eight.gg;

//#ifndef _GGCARD_H_
//#define _GGCARD_H_

//#include "wx/wx.h"
//#include "wx/filesys.h"
//#include "wx/fs_zip.h"
//#include "wx/xrc/xmlres.h"
//#include "wx/image.h"
////#include "wx/thread.h"
//#define GG_CARD_CARD_COUNT 52

//#define GG_CARD_SPADES 3
//#define GG_CARD_HEARTS 2
//#define GG_CARD_DIAMONDS 1
//#define GG_CARD_CLUBS 0

//#define GG_CARD_ACE 0
//#define GG_CARD_TWO 1
//#define GG_CARD_THREE 2
//#define GG_CARD_FOUR 3
//#define GG_CARD_FIVE 4
//#define GG_CARD_SIX 5
//#define GG_CARD_SEVEN 6
//#define GG_CARD_EIGHT 7
//#define GG_CARD_NINE 8
//#define GG_CARD_TEN 9
//#define GG_CARD_JACK 10
//#define GG_CARD_QUEEN 11
//#define GG_CARD_KING 12

//#define GG_CARD_BACK_1 20
//#define GG_CARD_BACK_2 21
//#define GG_CARD_JOKER_1 30
//#define GG_CARD_JOKER_2 31


//#define GG_CARD_TOTAL_SUITS 4
//#define GG_CARD_TOTAL_VALUES 13

//#define GG_CARD_WIDTH 71
//#define GG_CARD_HEIGHT 96

//#define GG_CARD_XRS ("cards.xrs")

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
	wxColour col_mask(*wxWHITE);

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
