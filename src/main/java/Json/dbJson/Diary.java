package Json.dbJson;


import java.util.Date;

public class Diary
{
    public int status;
    public int id;
    public Long anchor;
    public Diarybook diarybook = new Diarybook();
    public String htmlText;
    public String text;
    public Long date;
    public boolean islike = false;
    public float letterSpacing = (float)0.2;
    public int lineSpacingMultiplier = 0;
    public int lineSpacingExtra = 1;
    public float textSize = (float) 20;
    public int textAlignment = 0;
}
