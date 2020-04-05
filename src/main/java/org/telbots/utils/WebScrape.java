package org.telbots.utils;

import org.telbots.CovidIndiaBotConfig;
import org.telbots.model.StateInformation;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.ArrayList;
import java.util.List;

public class WebScrape {


    public List<StateInformation> getStatesInfo()
    {
        final String url = new CovidIndiaBotConfig().getPropValue("MOH_URL");
        ArrayList<StateInformation> stateInformation = new ArrayList<StateInformation>();
        try
        {
            final Document document =Jsoup.connect(url).validateTLSCertificates(false).get();

            for(Element row : document.select("table.table-striped tr"))
            {
                if(row.select("td:nth-child(1)").text().equals("") || row.select("td:nth-child(1)").text().contains("Total") || row.select("td:nth-child(1)").text().contains("States wise"))
                {
                    continue;
                }
                else
                {
                    StateInformation state = new StateInformation();

                    state.setsNo(Integer.parseInt(row.select("td:nth-child(1)").text()));
                    state.setName(row.select("td:nth-child(2)").text());
                    state.setCases(Integer.parseInt(row.select("td:nth-child(3)").text()));
                    state.setCured(Integer.parseInt(row.select("td:nth-child(4)").text()));
                    state.setDeaths(Integer.parseInt(row.select("td:nth-child(5)").text()));

                    stateInformation.add(state);
                }
            }

        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return stateInformation;
    }


}
