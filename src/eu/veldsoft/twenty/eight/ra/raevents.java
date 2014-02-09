

//




//




//



package eu.veldsoft.twenty.eight.ra;

//#ifndef _RAEVENTS_H
//#define _RAEVENTS_H

//#include "ra/racommon.h"

final wxEventType raINFO_EVT;
final wxEventType raBID_EVT;
final wxEventType raUPDATE_EVT;

class raInfoEvent : public wxEvent
{
	DECLARE_DYNAMIC_CLASS(raInfoEvent)
public:
	/** Default constructor */
	raInfoEvent();
	raInfoEvent(final raInfoEvent &evt);
	wxEvent *Clone(void) final { return new raInfoEvent(*this); }
	void SetCommand(int cmd);
	int GetCommand();
private:
	int m_cmd;
	// Disallow copy assignment operator
    raInfoEvent & operator=(final raInfoEvent &);

};

typedef void (wxEvtHandler.*raInfoEventFunction)(raInfoEvent&);

//#define EVT_INFO(fn) \
	DECLARE_EVENT_TABLE_ENTRY( \
	raINFO_EVT, wxID_ANY, wxID_ANY, \
	(wxObjectEventFunction)(wxEventFunction)(raInfoEventFunction)&fn, \
	(wxObject *) null \
	),

class raBidEvent : public wxEvent
{
	DECLARE_DYNAMIC_CLASS(raBidEvent)
public:
	/** Default constructor */
	raBidEvent();
	raBidEvent(final raBidEvent &evt);
	wxEvent *Clone(void) final { return new raBidEvent(*this); }
	void SetBid(int bid);
	int GetBid();
private:
	int m_bid;
	// Disallow copy assignment operator
    raBidEvent & operator=(final raBidEvent &);

};

typedef void (wxEvtHandler.*raBidEventFunction)(raBidEvent&);

//#define EVT_BID(fn) \
	DECLARE_EVENT_TABLE_ENTRY( \
	raBID_EVT, wxID_ANY, wxID_ANY, \
	(wxObjectEventFunction)(wxEventFunction)(raBidEventFunction)&fn, \
	(wxObject *) null \
	),
class raUpdateEvent : public wxEvent
{
	DECLARE_DYNAMIC_CLASS(raUpdateEvent)
public:
	/** Default constructor */
	raUpdateEvent();
	raUpdateEvent(final raUpdateEvent &evt);
	wxEvent *Clone(void) final { return new raUpdateEvent(*this); }
	void SetMessage(String msg);
	String GetMessage();
private:
	String m_msg;
	// Disallow copy assignment operator
    raUpdateEvent & operator=(final raUpdateEvent &);

};

typedef void (wxEvtHandler.*raUpdateEventFunction)(raUpdateEvent&);

//#define EVT_UPDATE(fn) \
	DECLARE_EVENT_TABLE_ENTRY( \
	raUPDATE_EVT, wxID_ANY, wxID_ANY, \
	(wxObjectEventFunction)(wxEventFunction)(raUpdateEventFunction)&fn, \
	(wxObject *) null \
	),
//#endif


//




//




//



//#include "ra/raevents.h"

final wxEventType raINFO_EVT = wxNewEventType();
final wxEventType raBID_EVT = wxNewEventType();
final wxEventType raUPDATE_EVT = wxNewEventType();

IMPLEMENT_DYNAMIC_CLASS(raInfoEvent, wxEvent)
//DEFINE_EVENT_TYPE(EVT_GAME_NEW)

raInfoEvent.raInfoEvent() : wxEvent( raINFO_EVT )
{
	// TODO : Use a //#defined constant here. Include relevant header in this cpp
	m_cmd = -1;
}
raInfoEvent.raInfoEvent(final raInfoEvent &evt)
{
	SetEventType(raINFO_EVT);
	m_cmd = evt.m_cmd;
}
void raInfoEvent.SetCommand(int cmd)
{
	m_cmd = cmd;
}
int raInfoEvent.GetCommand()
{
	return m_cmd;
}


IMPLEMENT_DYNAMIC_CLASS(raBidEvent, wxEvent)
//DEFINE_EVENT_TYPE(EVT_GAME_NEW)

raBidEvent.raBidEvent() : wxEvent( raBID_EVT )
{
	// TODO : Use a //#defined constant here. Include relevant header in this cpp
	m_bid = -1;
}
raBidEvent.raBidEvent(final raBidEvent &evt)
{
	SetEventType(raBID_EVT);
	m_bid = evt.m_bid;
}
void raBidEvent.SetBid(int bid)
{
	m_bid = bid;
}
int raBidEvent.GetBid()
{
	return m_bid;
}

IMPLEMENT_DYNAMIC_CLASS(raUpdateEvent, wxEvent)

raUpdateEvent.raUpdateEvent() : wxEvent( raUPDATE_EVT )
{

}
raUpdateEvent.raUpdateEvent(final raUpdateEvent &evt)
{
	SetEventType(raUPDATE_EVT);
	m_msg = evt.m_msg;
}
void raUpdateEvent.SetMessage(String msg)
{
	m_msg = msg;
}
String raUpdateEvent.GetMessage()
{
	return m_msg;
}


