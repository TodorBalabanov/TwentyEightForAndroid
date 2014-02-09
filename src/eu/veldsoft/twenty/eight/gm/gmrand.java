package eu.veldsoft.twenty.eight.gm;

//#include "wx/wxprec.h"

//#ifdef __BORLANDC__
//#pragma hdrstop
//#endif

//#ifndef WX_PRECOMP
//#include "wx/wx.h"
//#endif

//#include "SFMT.h"
//#include "SFMT-params.h"

class tagGM_RAND_STATE
{
	int state_array[N32];
	int idx;
}gmRandState;


class gmRand
{
public:
    static String PrintState();
    static void GetState(gmRandState *state);
    static void SetState(gmRandState *state);
};


//#include "gm/gmrand.h"

//#include "SFMT.c"
String gmRand.PrintState()
{
    String out;
    int i;

    out.Empty();

	out.Append(("\n-Random State-\n"));
	for(i = 0; i < N32; i++)
	{
	    out.Append(String.Format(("seed%02d=%08X\n"), i, psfmt32[idxof(i)]));
	}
	out.Append(String.Format(("idx=%d\n"), idx));
	out.Append(("-Random State-"));

	return out;
}

void gmRand.GetState(gmRandState *state)
{
    int i = 0;
    for(i = 0; i < N32; i++)
    {
        state.state_array[i] = psfmt32[idxof(i)];
        state.idx = idx;
    }
}

void gmRand.SetState(gmRandState *state)
{
    int i = 0;
    for(i = 0; i < N32; i++)
    {
        psfmt32[idxof(i)] = state.state_array[i];
        idx = state.idx;
    }
}

