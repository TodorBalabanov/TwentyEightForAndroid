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

//#ifndef _RACONFIG_H_
//#define _RACONFIG_H_

//#include "ra/racommon.h"
//#include "wx/config.h"

class tag_RA_CONF_APP_DATA
{
	int x;
	int y;
	int width;
	int height;
	boolean maximized;
} raConfAppData;

class tag_RA_CONF_GAME_DATA
{
	boolean clockwise;
	int min_bid3;
	boolean waive_rule4;
	boolean sluff_jacks;
} raConfGameData;

class tag_RA_CONF_PREFS_DATA
{
	int play_card_on;
	int card_back;
	boolean auto_play_single;
	boolean show_bid_bubbles;
} raConfPrefsData;

class tag_RA_CONF_DATA
{
	raConfAppData app_data;
	raConfGameData game_data;
	raConfPrefsData prefs_data;
} raConfData;

class raConfig
{
public:
	static raConfig *GetInstance();
	boolean Save();
	void GetData(raConfData *data);
	boolean SetData(raConfData *data);
	// Configure to make the destructor public with Visual Studio 6 - stupid compiler
//#ifdef _MSC_VER
	//#if _MSC_VER <= 1200
	virtual ~raConfig();
	//#endif
//#endif

private:

	// Disallow copy constructor/assignment operators
	raConfig(final raConfig &);
    raConfig & operator=(final raConfig &);

	wxConfig *m_config;
	raConfData m_data;

	raConfig();
////#ifndef __VISUALC6__
//	virtual ~raConfig();
////#endif
//#ifdef _MSC_VER
	//#if _MSC_VER > 1200
	virtual ~raConfig();
	//#endif
#else
	virtual ~raConfig();
//#endif
	static void Create();
	static raConfig *s_instance;
	static wxMutex s_mutex;

	static void SetDefaultValues(raConfData *data);
	boolean Load();

};
//#endif


//




//




//



//#include "ra/raconfig.h"

raConfig *raConfig.s_instance = null;
wxMutex raConfig.s_mutex;

//
// Public methods
//

raConfig *raConfig.GetInstance()
{
	// Double checked locking before creating an instance
	if(!s_instance)
	{
		wxMutexLocker lock(s_mutex);
		if(!lock.IsOk())
		{
			wxLogError(String.Format(("Failed to acquire mutex lock. %s:%d"), (__FILE__), __LINE__));
			return null;
		}
		if(!s_instance)
			Create();
	}

	return s_instance;
}

boolean raConfig.Save()
{
	if(!m_config.Write(raCONFPATH_APP_DATA_X, m_data.app_data.x))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Write(raCONFPATH_APP_DATA_Y, m_data.app_data.y))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Write(raCONFPATH_APP_DATA_WIDTH, m_data.app_data.width))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Write(raCONFPATH_APP_DATA_HEIGHT, m_data.app_data.height))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Write(raCONFPATH_APP_DATA_MAX, m_data.app_data.maximized))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Write(raCONFPATH_GAME_DATA_CLOCK, m_data.game_data.clockwise))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Write(raCONFPATH_GAME_DATA_MINBID3, m_data.game_data.min_bid3))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Write(raCONFPATH_GAME_DATA_WAIVERULE4, m_data.game_data.waive_rule4))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Write(raCONFPATH_GAME_DATA_SLUFFJACKS, m_data.game_data.sluff_jacks))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Write(raCONFPATH_PREFS_PLAYCARDON, m_data.prefs_data.play_card_on))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	if(!m_config.Write(raCONFPATH_PREFS_CARDBACK, m_data.prefs_data.card_back))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	if(!m_config.Write(raCONFPATH_PREFS_AUTOPLAYSINGLE, m_data.prefs_data.auto_play_single))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	if(!m_config.Write(raCONFPATH_PREFS_BIDBUBBLES, m_data.prefs_data.show_bid_bubbles))
	{
		wxLogError(String.Format(("m_config.Write() failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	return true;
}

void raConfig.GetData(raConfData *data)
{
	wxMutexLocker lock(s_mutex);
	memcpy(data, &m_data, sizeof(raConfData));
}
boolean raConfig.SetData(raConfData *data)
{
	wxMutexLocker lock(s_mutex);
	// TODO : Sanity check incoming data
	memcpy(&m_data, data, sizeof(raConfData));
	return true;
}

//
// Private constructor/destructor
//

raConfig.raConfig()
{
	memset(&m_data, 0, sizeof(m_data));
	m_config = new wxConfig(RA_APP_NAME);

	// If the application is being run for the first time,
	// configuration data may not be present. Create it.

	// Attempt to load the data from the configuration repository
	if(!Load())
	{
		// If load failed, the application is being run for the first time
		// Save default settings
		SetDefaultValues(&m_data);
		Save();
	}
}
raConfig.~raConfig()
{
	delete m_config;
}

//
// Private methods
//

void raConfig.Create()
{
	static raConfig the_instance;
	s_instance = &the_instance;
}

void raConfig.SetDefaultValues(raConfData *data)
{
	data.app_data.x = -1;
	data.app_data.y = -1;
	data.app_data.width = -1;
	data.app_data.height = -1;
	data.app_data.maximized = false;

	data.game_data.clockwise = true;
	data.game_data.min_bid3 = 23;
	data.game_data.waive_rule4 = false;
	data.game_data.sluff_jacks = true;

	data.prefs_data.play_card_on = raCONFIG_PREFS_PLAYCARDON_SCLICK;
	data.prefs_data.card_back = raCONFIG_PREFS_CARDBACK_BLUE;
	data.prefs_data.auto_play_single = true;
	data.prefs_data.show_bid_bubbles = true;

	return;
}

boolean raConfig.Load()
{
	if(!m_config.Read(raCONFPATH_APP_DATA_X, &m_data.app_data.x))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Read(raCONFPATH_APP_DATA_Y, &m_data.app_data.y))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Read(raCONFPATH_APP_DATA_WIDTH, &m_data.app_data.width))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Read(raCONFPATH_APP_DATA_HEIGHT, &m_data.app_data.height))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Read(raCONFPATH_APP_DATA_MAX, &m_data.app_data.maximized))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Read(raCONFPATH_GAME_DATA_CLOCK, &m_data.game_data.clockwise))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Read(raCONFPATH_GAME_DATA_MINBID3, &m_data.game_data.min_bid3))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Read(raCONFPATH_GAME_DATA_WAIVERULE4, &m_data.game_data.waive_rule4))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Read(raCONFPATH_GAME_DATA_SLUFFJACKS, &m_data.game_data.sluff_jacks))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}

	if(!m_config.Read(raCONFPATH_PREFS_PLAYCARDON, &m_data.prefs_data.play_card_on))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	if(!m_config.Read(raCONFPATH_PREFS_CARDBACK, &m_data.prefs_data.card_back))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	if(!m_config.Read(raCONFPATH_PREFS_AUTOPLAYSINGLE, &m_data.prefs_data.auto_play_single))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}
	if(!m_config.Read(raCONFPATH_PREFS_BIDBUBBLES, &m_data.prefs_data.show_bid_bubbles))
	{
		wxLogError(String.Format(("m_config.Read failed. %s:%d"), (__FILE__), __LINE__));
		return false;
	}


	return true;
}

