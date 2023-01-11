package com.chakra.projects.investment.service.amfi;

import com.chakra.projects.investment.Domain.Nav;
import com.chakra.projects.investment.utils.AmfiParser;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;

@Service
public class AMFI {
    public static HashMap<String, Nav> growthNavs = new HashMap<>();
    public static HashMap<String, Nav> divNavs = new HashMap<>();
    public static void initializeService() throws Exception {
        BufferedReader source = new BufferedReader(new InputStreamReader(new URL("https://www.amfiindia.com/spages/NAVAll.txt").openStream()));

        while (true) {
            String line = source.readLine();
            if (line == null) {
                break;
            }
            if (!AmfiParser.isDataLine(line)) {
                continue;
            }
            Nav nav = AmfiParser.parseAmfiResponse(line);
            if (nav == null) {
                continue;
            }
            if (nav.isGrowth()) {
                growthNavs.put(nav.getIsin(), nav);
            } else {
                divNavs.put(nav.getIsin(),nav);
            }
        }
        System.out.println("AMFI Service initialized");

    }

    public double getNav(String isin) {
        Nav nav = growthNavs.get(isin);
        if (nav != null) {
            return nav.getPrice();
        }

        nav = divNavs.get(isin);
        if (nav != null) {
            return nav.getPrice();
        }

        return 0;
    }



}
