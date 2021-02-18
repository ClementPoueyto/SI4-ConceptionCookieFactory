package fr.unice.polytech.recipe;

public enum CookingType {
    CRUNCHY,
    CHEWY;

    @Override
    public String toString(){
        String res;
        switch(this){
            case CRUNCHY:
                res = "Crunchy";
                break;
            case CHEWY:
                res = "Chewy";
                break;
            default:
                res = "no cooking";
        }
        return res;
    }

}

