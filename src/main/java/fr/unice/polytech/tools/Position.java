package fr.unice.polytech.tools;

public class Position{

    private final double latitude;
    private final double longitude;

    public Position(double latitude, double longitude){
        if(latitude<90 && latitude>-90) {
            this.latitude = latitude;
        }
        else{
            this.latitude=0;
        }
        if(longitude<180&& longitude>-180) {
            this.longitude = longitude;
        }
        else{
            this.longitude=0;
        }
    }

    public double getLatitude() {
        return latitude;
    }



    public double getLongitude() {
        return longitude;
    }


    /**
     * 
     * @param pos
     * @return double (distance in meters)
     * Mathematics formula to calculate distance between two (latitude, longitude) points.
     */
    public double distance(Position pos){
        if (pos!= null){
            final double R = 6371e3;

            double latShop = pos.getLatitude();
            double longShop = pos.getLongitude();
            double latCustomer = this.getLatitude();
            double longCustomer = this.getLongitude();
            double phi1 = latCustomer * Math.PI/180;
            double phi2 = latShop * Math.PI/180;
            double deltaPhi = (latShop - latCustomer) * Math.PI/180;
            double deltaLambda = (longShop - longCustomer) * Math.PI/180;

            double tmp = Math.sin(deltaPhi/2) * Math.sin(deltaPhi/2) + Math.cos(phi1) * Math.cos(phi2) * Math.sin(deltaLambda/2) * Math.sin(deltaLambda/2);
            double c = 2 * Math.atan2(Math.sqrt(tmp), Math.sqrt(1-tmp));

            return Math.round(R*c); // in meters, rounded to nearest int
        }
        else{
            return 0;
        }
    }

    @Override
    public String toString() {
        return "Position{" +
                "latitude=" + latitude +
                ", longitude=" + longitude +
                '}';
    }
}
