from keyapi.server import NetKeY

from keyapi.util import verify_file


def noop(param):
    pass

def methodfilter(classname, methodname):
    identifier = classname + "::" + methodname
    return (lambda c: (identifier in c.contractId.contractId))

def configure_callbacks(key):
    key.register_notification("client/taskStarted", noop)
    key.register_notification("client/taskFinished", noop)
    key.register_notification("client/taskProgress", noop)


if __name__ == "__main__":
    target = ("localhost", 5151)

    with NetKeY(target) as key:
        print(key.meta_version())
        configure_callbacks(key)
        filename = "/home/samuel/Dokumente/Projects/KeY/Demonstrator/ui/examples/ArrayMax.java"
        classname = "ArrayMax"
        methodname = "max"

        print(verify_file(key, filename, methodfilter(classname, methodname)))

    print("Terminating")
