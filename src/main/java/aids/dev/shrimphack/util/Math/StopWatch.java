package aids.dev.shrimphack.util.Math;

public class StopWatch
{
    private volatile long time;

    public boolean passed(double ms)
    {
        return System.currentTimeMillis() - time >= ms;
    }

    public boolean passed(long ms)
    {
        return System.currentTimeMillis() - time >= ms;
    }

    public StopWatch reset()
    {
        time = System.currentTimeMillis();
        return this;
    }

    public long getTime()
    {
        return System.currentTimeMillis() - time;
    }

    public void setTime(long ns)
    {
        time = ns;
    }

}