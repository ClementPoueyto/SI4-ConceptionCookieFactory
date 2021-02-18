package fr.unice.polytech.recipe;

public enum MixType {
    MIXED,
    TOPPED;

    @Override
    public String toString(){
        String res;
        switch(this){
            case MIXED:
                res = "Mixed";
                break;
            case TOPPED:
                res = "Topped";
                break;
            default:
                res = "no mix";
        }
        return res;
    }
}
