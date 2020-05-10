/**module schach {
 requires javafx.controls;
 requires transitive javafx.graphics;

 exports schach;
 opens schach;
 }*/
open module schach {
    requires javafx.controls;
    requires transitive javafx.graphics;

    exports schach;
}