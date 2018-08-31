package Json.dbJson;


import java.util.Date;

public class Diary
{
    public int status;
    public long id;
    public long modified;
    public Diarybook diarybook = new Diarybook();
    public String htmlText;
    public String text;
    public long date;
    public boolean islike = false;
    public int fontType;
    public int alignmentType;
    public int background;
    public float letterSpacing = (float)0.2;
    public int lineSpacingMultiplier = 0;
    public int lineSpacingExtra = 1;
    public float textSize = (float) 20;
    public int textAlignment = 0;
}
