module notes.rest {
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.beans;
    requires spring.core;
    requires spring.context;
    requires spring.data.rest.core;
    requires spring.data.commons;
    requires transitive spring.web;
    requires transitive spring.webmvc;

    requires transitive notes.core;
    requires transitive com.fasterxml.jackson.databind;
    requires transitive com.fasterxml.jackson.core;

    opens rest to spring.beans, spring.context, spring.web, spring.core;
    opens rest.exceptions to spring.beans, spring.context, spring.web, spring.core;
    exports rest;
    exports rest.exceptions;
}
