package java.io;

public class PrintStream extends java.io.FilterOutputStream {

    /*@ public normal_behavior
        ensures \invariant_for(this);
     */
    public PrintStream(java.io.OutputStream out);

    /*@ public normal_behavior
        ensures \invariant_for(this);
    */
    public PrintStream(java.io.OutputStream out, boolean autoFlush);


    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void print(boolean b);

    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void print(char c);

    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void print(int i);

    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void print(long l);

    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void print(float f);

    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void print(double d);
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void print(char[] s);
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void print(java.lang.String s);
    public void print(java.lang.Object obj);
    public void printf(java.lang.String s, Object... args);
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
     */
    public void println();
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void println(boolean x);
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void println(char x);
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void println(int x);
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void println(long x);
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void println(float x);

    public void println(double x);
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void println(char[] x);
    /*@ public normal_behavior
        ensures \invariant_for(this);
        assignable \nothing;
    */
    public void println(java.lang.String x);
    public void println(java.lang.Object x);
}
