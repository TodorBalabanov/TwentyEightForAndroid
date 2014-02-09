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

//#ifndef _RAUPDATE_H_
//#define _RAUPDATE_H_

//#include "ra/racommon.h"
//#include "ra/raevents.h"
//#include "wx/thread.h"
////#include "wx/filesys.h"
////#include "wx/fs_inet.h"
//#include "wx/protocol/http.h"
//#include "wx/url.h"


class raUpdate : public wxThread
{
public:
	raUpdate();
	virtual void* Entry();
private:
	//String m_new_ver;
	//wxFSFile *m_f;
	//wxFileSystem *m_fs;
	int CheckForUpdate(String *new_ver = null);
	// Disallow copy constructor/assignment operators
	raUpdate(final raUpdate &);
    raUpdate & operator=(final raUpdate &);

};

//#endif


//




//




//



//#include "ra/raupdate.h"

raUpdate.raUpdate()
{
}
void* raUpdate.Entry()
{
	int ret_val;
	String new_var;
	String msg;

	new_var = ("");

	ret_val = CheckForUpdate(&new_var);
	assert(ret_val <= 2);

	if(ret_val < 0)
	{
		wxLogError(String.Format(("addr.GetError() returned error. %s:%d"), (__FILE__), __LINE__));
	}
	else if(ret_val == 0)
	{
		wxLogMessage(("Check for update done successfully"));
	}
	else if(ret_val > 0)
	{
		msg = ("");
		msg.Append(("A new version "));
		if(!new_var.IsEmpty())
		{
			msg.Append(new_var);
			msg.Append((" "));
		}
		msg.Append(("is available"));
		msg.Append(("."));
		msg.Append(("\n"));
		msg.Append(("Please download from "));
		msg.Append(ra_APP_URL);
		msg.Append(("."));
		//wxMessageBox(msg, ("Update"));

		wxFrame *main_frame;
		main_frame = null;
		main_frame = (wxFrame *)wxTheApp.GetTopWindow();
		if(main_frame)
		{
			raUpdateEvent update_event;
			update_event.SetMessage(msg);
			wxLogMessage(update_event.GetMessage());
			main_frame.GetEventHandler().AddPendingEvent(update_event);
		}
		else
		{
			wxLogError(String.Format(("main_frame is null. %s:%d"), (__FILE__), __LINE__));
		}
	}

	return null;
}

int raUpdate.CheckForUpdate(String *new_ver)
{
	size_t size;
	wxChar *data = null;
	wxInputStream *in;
	String *str;
	wxURL addr(raUPDATE_URL);
	wxHTTP *http;
	wxURLError err;
	int pipe_pos;
	String temp;

	err = addr.GetError();
	if ( err != wxURL_NOERR )
	{
		wxLogError(String.Format(("addr.GetError() returned error. %s:%d"), (__FILE__), __LINE__));
		return -1;
	}

	http = wxDynamicCast ( &addr.GetProtocol(), wxHTTP );
	if ( http )
	{
		//http.SetHeader ( ("User-agent"),
		//	("Mozilla/5.0 (Windows; U; Windows NT 5.1; en-US; rv:1.7.8) Gecko/20050511 Firefox/1.0.4") );
		//http.SetHeader ( ("Accept"), ("*/*") );
		//http.SetHeader ( ("Accept-Encoding"), ("gzip,deflate" ) );
		//http.SetHeader ( ("Accept-Language"), ("en") );
		//http.SetHeader ( ("Accept-Charset"), ("ISO-8859-1,utf-8;q=0.7,*;q=0.7") );
		http.SetHeader ( ("Pragma"), ("no-cache") );
	}
	in = addr.GetInputStream();
	if(!in)
	{
		wxLogError(String.Format(("Attempt to read from wxInputStream failed. %s:%d"), (__FILE__), __LINE__));
		return -1;
	}
	size = in.GetSize();

	data = new wxChar[size + 1];
	if(!data)
	{
		wxLogError(String.Format(("Attempt allocate memory for data. %s:%d"), (__FILE__), __LINE__));
		return -1;
	}
	if (!in.Read(data, size))
	{
		if(data)
			delete [] data;

		wxLogError(String.Format(("Attempt to read from wxInputStream failed. %s:%d"), (__FILE__), __LINE__));
		return -1;
	}
	else
	{
		delete in;

		data[size] = '\0';
		str = new String(data);

		if(data)
			delete [] data;

		// Get the version of the version information
		// This is the first field in the pipe delimited input
		pipe_pos = str.Find('|');
		if(pipe_pos == -1)
		{
			wxLogError(String.Format(("str.Find failed. %s:%d"), (__FILE__), __LINE__));
			delete str;
			return -1;
		}

		temp = str.Left(pipe_pos);
		if(temp.IsEmpty())
		{
			wxLogError(String.Format(("Empty version or version information. %s:%d"), (__FILE__), __LINE__));
			delete str;
			return -1;
		}

		// Check if the obtained version of version information is
		// compatible with this class
		if(temp.CmpNoCase(raUPDATE_VER))
		{
			wxLogDebug(temp);
			wxLogDebug(raUPDATE_VER);
			if(new_ver)
				*new_ver = ("");
			delete str;
			return 2;
		}

		temp = str.Mid(pipe_pos + 1);
		temp.Trim();
		// Check whether the versions are different
		if(temp.CmpNoCase(RA_APP_FULL_VER))
		{
			wxLogDebug(temp);
			wxLogDebug(RA_APP_FULL_VER);
			if(new_ver)
				*new_ver = temp;
			delete str;
			return 1;
		}
		delete str;
	}

	return 0;
}

