package uk.co.evoco.testdata;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankHoliday {

    private String title;
    private LocalDate localDate;

    public String getTitle() {
        return title;
    }

    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocalDate(String dateFormat) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormat.forPattern(dateFormat);
        return this.localDate.toString(dateTimeFormatter);
    }

    public LocalDate getLocalDate() {
        return this.localDate;
    }

    @JsonProperty("date")
    public void setLocalDate(String localDate) {
        this.localDate = LocalDate.parse(localDate);
    }
}
