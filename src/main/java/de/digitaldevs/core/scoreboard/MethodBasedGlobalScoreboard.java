package de.digitaldevs.core.scoreboard;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author MerryChrismas
 * @author <a href='https://digitaldevs.de'>DigitalDevs.de</a>
 * @version 1.0.0
 */
public class MethodBasedGlobalScoreboard extends GlobalScoreboard {

    private String title = "";
    private List<String> lines = new ArrayList<>();

    public MethodBasedGlobalScoreboard() {
        this.setTitleSupplier(() -> this.title);
        this.setLinesSupplier(() -> this.lines);
    }

    public void setTitle(String title) {
        this.title = title;
        this.updateScoreboard();
    }

    public void setLines(List<String> lines) {
        this.lines = lines;
        this.updateScoreboard();
    }

    public void setLines(String... lines) {
        this.setLines(Arrays.asList(lines));
    }

}
