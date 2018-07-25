package Json.dbJson;

import Json.dbJson.Label;
import Json.dbJson.Sentence;

public class SentenceLabel {
    public int status;
    public int id;
    public long anchor;
    public Sentence sentence = new Sentence();
    public Label label = new Label();
}
