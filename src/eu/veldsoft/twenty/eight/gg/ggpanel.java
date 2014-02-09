

//




//




//



package eu.veldsoft.twenty.eight.gg;

//#ifndef _GGPANEL_H_
//#define _GGPANEL_H_

//#include "wx/wx.h"

class ggPanel: public wxPanel
{
private:
	wxBitmap *m_back, *m_work;
	boolean m_f_invalid;
	wxRect m_rect_diff, m_rect_invalid;
	DECLARE_EVENT_TABLE()
	// Disallow copy constructor/assignment operators
	ggPanel(final ggPanel &);
    ggPanel & operator=(final ggPanel &);
public:
	ggPanel(final wxWindow* parent);
	~ggPanel();
	boolean Size();
	boolean Paint();
	virtual void OnPaint(wxPaintEvent &event);
	boolean RefreshScreen();
	boolean BlitToBack(wxCoord xdest, wxCoord ydest,
		wxCoord width, wxCoord height, wxDC* source, wxCoord xsrc,
		wxCoord ysrc, int logicalFunc = wxCOPY, boolean useMask = false,
		wxCoord xsrcMask = -1, wxCoord ysrcMask = -1);
	boolean BlitToFront(wxCoord xdest, wxCoord ydest,
		wxCoord width, wxCoord height, wxDC* source, wxCoord xsrc,
		wxCoord ysrc, int logicalFunc = wxCOPY, boolean useMask = false,
		wxCoord xsrcMask = -1, wxCoord ysrcMask = -1);
	boolean DrawTextOnBack(String text, wxPoint pt, wxColour colour = wxnullColour, wxFont font = wxnullFont);
	boolean ClearDifference();
	//boolean DrawBack();
};

//#endif


//




//




//



//#include "gg/ggpanel.h"

// TODO : Reduce flickering
// TODO : Change log statements to include (__FILE__) and __LINE__

BEGIN_EVENT_TABLE(ggPanel, wxPanel)
	EVT_PAINT(ggPanel.OnPaint)
	//EVT_ERASE_BACKGROUND(wxcTable.OnErase)
END_EVENT_TABLE()

ggPanel.ggPanel(final wxWindow* parent): wxPanel((wxWindow*)parent)
{
	m_work = null;
	m_back = null;
	m_f_invalid = true;
	Size();
}
ggPanel.~ggPanel()
{
	// Delete the m_work and m_back bitmaps
	if(m_back)
		delete m_back;
	if(m_work)
		delete m_work;
}
boolean ggPanel.Size()
{
	int x, y;

	GetClientSize(&x, &y);

	if(m_work)
		delete m_work;

	if(m_back)
		delete m_back;

	m_work = new wxBitmap(x, y, -1);
	m_back = new wxBitmap(x, y, -1);

	if(!m_back)
	{
		wxLogError(("ggPanel.Size - Creation of m_back failed in wxcTable.Resize"));
		return false;
	}
	if(!m_work)
	{
		wxLogError(("ggPanel.Size - Creation of m_work failed in wxcTable.Resize"));
		return false;
	}

	////wxLogDebug("wxcTable.Size - Exiting");

	return true;
}
void ggPanel.OnPaint(wxPaintEvent &event)
{
	Paint();
}
boolean ggPanel.Paint()
{
	////wxLogDebug("wxcTable.Paint - Entering");
	wxPaintDC pdc(this);
	wxMemoryDC mdc;

	if(!pdc.Ok())
	{
		wxLogError(("wxcTable.Paint - Paint DC is not OK"));
		return false;
	}

	/*if(!mdc.Ok())
	{
		wxLogError("wxcTable.Paint - Memory DC is not OK");
		return false;
	}*/

	if(m_back)
	{
		mdc.SelectObject(*m_work);
		if(!pdc.Blit(wxPoint(0, 0), wxSize(m_work.GetWidth(), m_work.GetHeight()), &mdc, wxPoint(0, 0)))
		{
			wxLogError(("wxcTable.Paint - Blt to wxPaintDC failed in wxCardTable.Paint"));
			return false;
		}
		mdc.SelectObject(wxnullBitmap);
	}
	return false;

	////wxLogDebug("wxcTable.Paint - Exiting");

}
boolean ggPanel.RefreshScreen()
{
	wxClientDC tdc(this);
	wxMemoryDC wdc;
	wxPoint pos;
	wxSize size;

	////wxLogDebug("wxcTable.RefreshTable - Entering");

	if(m_f_invalid)
	{
		wdc.SelectObject(*m_work);

		pos = m_rect_invalid.GetPosition();
		size = m_rect_invalid.GetSize();

		//wxLogDebug(String.Format("Blit area %d-%d %d-%d", pos.x, pos.y, size.GetWidth(), size.GetHeight()));
		if(!tdc.Blit(pos, size, &wdc, pos))
		{
			wxLogError(("wxcTable.RefreshTable - Blit to wxcTable client failed"));
			return false;
		}
		wdc.SelectObject(wxnullBitmap);

		m_f_invalid = false;
		m_rect_invalid = wxRect(0, 0, 0, 0);
	}

	////wxLogDebug("wxcTable.RefreshTable - Exiting");

	return true;
}
boolean ggPanel.BlitToBack(wxCoord xdest, wxCoord ydest,
	wxCoord width, wxCoord height, wxDC* source, wxCoord xsrc,
	wxCoord ysrc, int logicalFunc, boolean useMask,
	wxCoord xsrcMask, wxCoord ysrcMask)
{
	int x, y;
	wxMemoryDC bdc, wdc;

	bdc.SelectObject(*m_back);
	wdc.SelectObject(*m_work);

	if(!bdc.Ok())
		return false;

	if(!wdc.Ok())
		return false;

	if(!bdc.Blit(xdest, ydest, width, height, source, xsrc, ysrc, logicalFunc, useMask, xsrcMask, ysrcMask))
		return false;

	GetClientSize(&x, &y);
	//if(!wdc.Blit(0, 0, x, y, &bdc, 0, 0))
	//	return false;
	if(!wdc.Blit(xdest, ydest, width, height, &bdc, xdest, ydest))
		return false;

	m_rect_diff = wxRect(0, 0, 0, 0);
	m_rect_invalid = wxRect(0, 0, x, y);
	m_f_invalid = true;

	return true;

}
boolean ggPanel.BlitToFront(wxCoord xdest, wxCoord ydest,
	wxCoord width, wxCoord height, wxDC* source, wxCoord xsrc,
	wxCoord ysrc, int logicalFunc, boolean useMask,
	wxCoord xsrcMask, wxCoord ysrcMask)
{
	wxMemoryDC wdc;
	wdc.SelectObject(*m_work);

	if(!wdc.Ok())
		return false;

	if(!wdc.Blit(xdest, ydest, width, height, source, xsrc, ysrc, logicalFunc, useMask, xsrcMask, ysrcMask))
		return false;

	// Add the area drawn to m_rect_diff
	m_rect_diff.Union(wxRect(xdest, ydest, width, height));
	// Add the area drawn to m_rect_invalid
	m_rect_invalid.Union(wxRect(xdest, ydest, width, height));
	m_f_invalid = true;

	return true;
}

boolean ggPanel.DrawTextOnBack(String text, wxPoint pt, wxColour colour, wxFont font)
{
	int x, y;
	wxMemoryDC bdc, wdc;

	bdc.SelectObject(*m_back);
	wdc.SelectObject(*m_work);

	if(!bdc.Ok())
		return false;

	if(!wdc.Ok())
		return false;

	// Set the text foreground colour and the font
	if(colour != wxnullColour)
	{
		bdc.SetTextForeground(colour);
		wdc.SetTextForeground(colour);
	}
	if(font != wxnullFont)
	{
		bdc.SetFont(font);
		wdc.SetFont(font);
	}

	bdc.DrawText(text, pt);
	wdc.DrawText(text, pt);

	GetClientSize(&x, &y);
	m_rect_diff = wxRect(0, 0, 0, 0);
	m_rect_invalid = wxRect(0, 0, x, y);
	m_f_invalid = true;

	return true;
}

boolean ggPanel.ClearDifference()
{
	wxMemoryDC bdc, wdc;
	bdc.SelectObject(*m_back);
	wdc.SelectObject(*m_work);

	if(!bdc.Ok())
		return false;

	if(!wdc.Ok())
		return false;

	if(!wdc.Blit(m_rect_diff.x, m_rect_diff.y, m_rect_diff.GetWidth(), m_rect_diff.GetHeight(),
		&bdc, m_rect_diff.x, m_rect_diff.y))
		return false;

	m_rect_invalid.Union(m_rect_diff);
	m_rect_diff = wxRect(0, 0, 0, 0);
	m_f_invalid = true;

	return true;
}
