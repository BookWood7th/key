import os
import shutil
import tempfile

from .keydata import LoadParams, StrategyOptions
from .server import KeYEnv, KeYProof


def verify_file(key, filename, contractfilter, strategy_options = StrategyOptions(None, None, None, None, None, 10000)):
    with tempfile.TemporaryDirectory() as tmpdirname:
        # Copy filename to tmpdirname
        tmpfile = os.path.join(tmpdirname, os.path.basename(filename))
        shutil.copy(filename, tmpfile)

        # params = LoadParams("/home/samuel/Dokumente/Projects/KeY/key/key.core.example/example/IntegerUtil.java",
        params = LoadParams(tmpfile,
                            None,
                            None,
                            None)

        with KeYEnv(key, params) as env:
            contracts = env.contracts()
            contracts = list(filter(contractfilter, contracts))
            checked_contracts = [c.contractId.contractId for c in contracts]
            results = {}
            for (label, contract) in zip(checked_contracts, contracts):
                with KeYProof(key, contract) as proof:
                    status = proof.auto(options = strategy_options)
                    if status.openGoals == 0:
                        results[label] = {"verified": True}
                    else:
                        open_goals = proof.goals(open_only=True)
                        goal_descriptions = [goal.description for goal in open_goals]
                        results[label] = {"verified": False, "open_goals": goal_descriptions}
            return results