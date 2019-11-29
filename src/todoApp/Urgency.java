package todoApp;

public enum Urgency {

        NOT_URGENT(1),
        NORMAL(2),
        URGENT(3);

    public int urgencyValue;


    Urgency(int value){urgencyValue = value;}


    public int getUrgencyValue() {
        return urgencyValue;
    }


}
