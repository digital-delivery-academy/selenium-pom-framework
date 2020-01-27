package uk.co.evoco.webdriver.utils.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import uk.co.evoco.webdriver.utils.JsonUtils;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BankHolidays {

    private Locale locale;
    private List<BankHoliday> englandAndWalesBankHolidays;
    private List<BankHoliday> scotlandBankHolidays;
    private List<BankHoliday> northernIrelandBankHolidays;

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(String locale) {
        this.locale = Locale.valueOf(locale.replace("-", "_").toUpperCase());
    }

    @JsonProperty("england-and-wales")
    public void setEnglandAndWalesHolidays(JsonNode jsonNode) throws JsonProcessingException {
        this.englandAndWalesBankHolidays = JsonUtils.fromString(jsonNode.get("events").toString(), new TypeReference<>() {
        });
    }

    @JsonProperty("scotland")
    public void scotlandHolidays(JsonNode jsonNode) throws JsonProcessingException {
        this.scotlandBankHolidays = JsonUtils.fromString(jsonNode.get("events").toString(), new TypeReference<>() {});
    }

    @JsonProperty("northern-ireland")
    public void northernIrelandHolidays(JsonNode jsonNode) throws JsonProcessingException {
        this.northernIrelandBankHolidays = JsonUtils.fromString(jsonNode.get("events").toString(), new TypeReference<>() {});
    }

    public List<BankHoliday> get(Locale locale) {
        switch(locale) {
            case ENGLAND_AND_WALES:
                return this.englandAndWalesBankHolidays;
            case SCOTLAND:
                return this.scotlandBankHolidays;
            case NORTHERN_IRELAND:
                return this.northernIrelandBankHolidays;
            default:
                throw new IllegalArgumentException("Locale must be ENGLAND_AND_WALES, SCOTLAND or NORTHERN_IRELAND");
        }
    }
}
