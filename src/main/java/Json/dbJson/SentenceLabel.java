package Json.dbJson;

import Json.dbJson.Label;
import Json.dbJson.Sentence;

public class SentenceLabel {
    public int status;
    public long id;
    public long modified;
    public Sentence sentence = new Sentence();
    public Label label = new Label();
}
