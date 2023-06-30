package GOL;

/**
 * @author  Melis Yade Layik, Sinan Ince, Konuralp Celikyay, Benno Dinsch, Lau Kailany
 * @version 1, 21.06.2023
 **/



public enum FigurenT {
    GLIDER("3o$2bo$bo!"), //string übergeben welches ein glider ist
    BOGEN("bo$obo$o2bo$2bo$4o$bob2obo$3o4b2o$7b2o$3o4b2o$bob2obo$4o$2bo$o2bo$obo$bo!"),
    MYFORM("7b3o$6bo2bo$7bo3bo$2bo5b3o$3bo5bo$2ob2o$o2bo9bo$obo9bobo$bo9bo2bo$10b2ob2o$5bo5bo$4b3o5bo$3bo3bo$5bo2bo$5b3o!"),
    SCHMETTERLING("2o$obo2b2o$bo4bo$2b4o2$2b4o$bo4bo$obo2b2o$2o!");

    private final String text;

    FigurenT(String s) {
        text = s;
    }


    public String getValue() {
        return text;

    }
}
