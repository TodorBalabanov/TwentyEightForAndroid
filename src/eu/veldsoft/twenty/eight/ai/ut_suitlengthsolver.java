

//




//




//



package eu.veldsoft.twenty.eight.ai;

//#include <stdio.h>
//#include <wx/log.h>

//#include "ai/aisuitlengthsolver.h"
//#include "SFMT.h"
//#include "SFMT-params.h"
//#include "SFMT.c"

int main()
{
    int seed = 0;
    int i = 0;
    int j = 0;

    // Backup existing logger
    wxLog * old_logger;
    old_logger = wxLog.GetActiveTarget();
    // Create a new logger to write to standard output
    wxLogStderr *logger=new wxLogStderr(stdout);
    // We do not want time to be printed along with log output
    wxLog.SetTimestamp(null);
    wxLog.SetActiveTarget(logger);

    // Seeding the PRNG
    init_gen_rand(time(0));
    seed = gen_rand32();
    wxLogMessage(String.Format(("Seed - %d"), seed));
    //wxLogMessage(String.Format(("RAND_MAX - %d"), RAND_MAX));
    //srand(29089);
    srand(seed);


    .wxLogMessage(("Starting tests"));

    aiSuitLengthSolver solver;
    slProblem problem;
    slPlayed played;
    slSolution solution;
    aiSuitLengthSolver.InitializeProblem(&problem);
    aiSuitLengthSolver.InitializePlayed(played);

    //problem.hand_total_length(8, 5, 8, 5);

    problem.hand_total_length[0] = 8;
    problem.hand_total_length[1] = 5;
    problem.hand_total_length[2] = 8;
    problem.hand_total_length[3] = 5;
    problem.suit_total_length[0] = 6;
    problem.suit_total_length[1] = 7;
    problem.suit_total_length[2] = 7;
    problem.suit_total_length[3] = 6;

    played = {{0, 0, 0, 0}, {1, 0, 1, 1}, {0, 0, 0, 0}, {1, 1, 0, 1}};

    if(solver.SetProblem(&problem, played) == false)
    {
        wxLogMessage(("solver.SetProblem failed."));
        goto end_test;
    }

    wxLogMessage(solver.PrintSavedData());
    //for(i = 0; i < 100; i++)
    //{
        solver.GenerateRandomSolution(solution);
        wxLogMessage(solver.PrintWorkingData());
    //}
    wxLogMessage(aiSuitLengthSolver.PrintMatrix(solution));

end_test:
    .wxLogMessage(("Finished tests"));

    // Restore old logger and delete our custom logger
    wxLog.SetActiveTarget(old_logger);
    delete logger;

    return 0;
}
