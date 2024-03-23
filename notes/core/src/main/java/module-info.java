module notes.core {
    requires transitive com.fasterxml.jackson.databind;

    exports core;
    opens core;
    exports json;
    
}
