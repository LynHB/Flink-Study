package com.study.demo;

import com.clickhouse.client.*;
import com.clickhouse.data.ClickHouseFormat;

public class Main {
    public static void main(String[] args) throws ClickHouseException {
        ClickHouseNodes servers = ClickHouseNodes.of(
                "http://bigdata-master:8123,bigdata-slave01:8123,bigdata-slave02:8123/wy"
                        + "?load_balancing_policy=random&health_check_interval=5000&failover=2"
                        + "&user=wy_user&password=wy_user_password");

        try (ClickHouseClient client = ClickHouseClient.newInstance(ClickHouseProtocol.HTTP);
             ClickHouseResponse response = client.read(servers)
                     .format(ClickHouseFormat.RowBinaryWithNamesAndTypes)
                     .query("select * from access_history_distributed limit :limit")
                     .params(1000)
                     .executeAndWait()) {
            ClickHouseResponseSummary summary = response.getSummary();
            long totalRows = summary.getTotalRowsToRead();
            System.out.println(totalRows);
        }
    }

}
