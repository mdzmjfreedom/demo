package com.example.demo.util;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.symmetric.SymmetricAlgorithm;
import cn.hutool.crypto.symmetric.SymmetricCrypto;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class AESUtil {

    private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private static SymmetricCrypto AES = null;

    static {
        byte[] keys = SecureUtil.generateKey(SymmetricAlgorithm.AES.getValue()).getEncoded();
        AES = new SymmetricCrypto(SymmetricAlgorithm.AES, keys);
    }

    public static String encrypt(String data) {
        return AES.encryptHex(data);
    }

    public static String decrypt(String data) {
        return AES.decryptStr(data, Charset.defaultCharset());
    }

    public static void main(String[] args) {
        String fileName = "C:\\Users\\Administrator\\Desktop\\1.xlsx";
        EasyExcel.read(fileName, Data.class, new AnalysisEventListener<Data>() {
            List<Data> dataList = new ArrayList<>();

            @Override
            public void invoke(Data rowData, AnalysisContext context) {
                // 处理每一行数据
                dataList.add(rowData);
            }

            @Override
            public void doAfterAllAnalysed(AnalysisContext context) {
                for (Data rowData : dataList) {
                    try {
                        HttpRequest ewbHttp = HttpUtil.createPost("https://qy-service.tms.ztocc.com/tms/ewb/getInfo");
                        ewbHttp.body("{\"ewbNo\":\"" + rowData.getWaybillNo() + "\"}");
                        HttpResponse ewbResponse = ewbHttp.execute();
                        JSONObject ewbRes = JSONUtil.toBean(ewbResponse.body(), JSONObject.class);

                        if (!ewbRes.getBool("status")) {
                            log.info("运单号:{}，接口获取失败", rowData.getWaybillNo());
                            continue;
                        }

                        JSONObject ewbResult = ewbRes.getJSONObject("result");
                        if (ObjectUtil.isEmpty(ewbResult)) {
                            log.info("运单号:{}，结果为空", rowData.getWaybillNo());
                            continue;
                        }
                        log.info("运单号:{}，寄件网点编码:{}", rowData.getWaybillNo(), ewbResult.getStr("sendSiteCode"));
                    } catch (Exception e) {
                        e.printStackTrace();
                        log.info("运单号:{}，获取出错，错误信息:{}", rowData.getWaybillNo(), e.getMessage());
                        continue;
                    }
                }
            }
        }).sheet(0).doRead();

//        String filePath = "C:\\Users\\Administrator\\Desktop\\3.json";
//        StringBuilder sb = new StringBuilder();
//        try {
//            Path path = Paths.get(filePath);
//            List<String> lines = Files.readAllLines(path);
//
//            for (String line : lines) {
//                sb = sb.append(line);
//            }
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        List<String> list = JSONUtil.parseArray(sb.toString()).toList(String.class);
//        for (String s : list) {

        // 结算交易记录
//            HttpRequest http = HttpUtil.createPost("https://ztocc-sys.gw.zt-express.com/phecda/account/settleTrans/sumTransDetails");
//            http.header("authtoken", "u8XMcdQ6ZvDxNPjVXWVuCQdfTjJiZ2QBgF4g470Wx2dezZs7twqcA");
//            http.body("{\"page\":1,\"pageSize\":10,\"hidePlatform\":0,\"createTimeStart\":\"2023-09-06 00:00:00\",\"createTimeEnd\":\"2023-09-06 23:59:59\",\"dataIds\":\"" + s + "\"}");
//            HttpResponse response = http.execute();
//            JSONObject result = JSONUtil.toBean(response.body(), JSONObject.class);
//            if (result.getBool("status")) {
//                JSONObject jieguo = result.getJSONObject("result");
//                log.info("运单号:{}               {}                  {}", s, jieguo.getBigDecimal("incomeTotal"), jieguo.getBigDecimal("payTotal"));
//            }

//            报价计算
//            try {
//                HttpRequest ewbHttp = HttpUtil.createPost("https://ztocc-sys.gw.zt-express.com/api/phecda/data/getEwbInfo");
//                ewbHttp.header("authtoken", "u8XMcdQ6ZpEiXs8ndW__nXGM_KwbkzgL_18ofwLUzSj1XZ6E7Itbw");
//                ewbHttp.body("{\"ewbNo\":\"" + s + "\"}");
//                HttpResponse ewbResponse = ewbHttp.execute();
//                JSONObject ewbRes = JSONUtil.toBean(ewbResponse.body(), JSONObject.class);
//                if (!ewbRes.getBool("status")) {
//                    log.info("运单号:{}，获取运单信息失败", s);
//                }
//                JSONObject ewbResult = ewbRes.getJSONObject("result");
//
//                HttpRequest calcHttp = HttpUtil.createPost("https://ztocc-sys.gw.zt-express.com/api/phecda/quote/calculateTest");
//                calcHttp.header("authtoken", "u8XMcdQ6ZpEiXs8ndW__nXGM_KwbkzgL_18ofwLUzSj1XZ6E7Itbw");
//
//                String body = "{\n" +
//                        "\t\"inventoryIncrementFlag\": 2,\n" +
//                        "\t\"specialPolicyFlag\": 0,\n" +
//                        "\t\"ewbNo\": \"" + s + "\",\n" +
//                        "\t\"productTypeCode\": \"" + ewbResult.getStr("productType") + "\",\n" +
//                        "\t\"serviceModeCode\": \"" + ewbResult.getStr("serviceMode") + "\",\n" +
//                        "\t\"goodsTypeCode\": \"" + ewbResult.getStr("goodsType") + "\",\n" +
//                        "\t\"returnBillTypeCode\": \"" + ewbResult.getStr("returnBillTypeCode") + "\",\n" +
//                        "\t\"deliveryMode\": \"" + ewbResult.getStr("deliveryMode") + "\",\n" +
//                        "\t\"carTypeCode\": \"" + ewbResult.getStr("carTypeCode") + "\",\n" +
//                        "\t\"declarePrice\": \"" + ewbResult.getInt("claimingValue") + "\",\n" +
//                        "\t\"codFee\": \"" + ewbResult.getInt("codFee") + "\",\n" +
//                        "\t\"createBillTime\": \"" + dateFormat.format(new Date(ewbResult.getLong("sendDate"))) + "\",\n" +
//                        "\t\"calcWeight\": \"" + ewbResult.getBigDecimal("weight") + "\",\n" +
//                        "\t\"qtyNum\": \"" + ewbResult.getInt("qty") + "\",\n" +
//                        "\t\"sendSiteCode\": \"" + ewbResult.getStr("sendSiteCode") + "\",\n" +
//                        "\t\"receiveSiteCode\": \"" + ewbResult.getStr("dispatchSiteCode") + "\",\n" +
//                        "\t\"sendInfo\": {\n" +
//                        "\t\t\"provinceCode\": \"" + ewbResult.getStr("sendProvinceCode") + "\",\n" +
//                        "\t\t\"cityCode\": \"" + ewbResult.getStr("sendCityCode") + "\",\n" +
//                        "\t\t\"districtCode\": \"" + ewbResult.getStr("sendDistrictCode") + "\",\n" +
//                        "\t\t\"townCode\": \"\",\n" +
//                        "\t\t\"customAreaCode\": " + ewbResult.getStr("sendCustomAreaCode") + ",\n" +
//                        "\t\t\"sendStaff\": null,\n" +
//                        "\t\t\"sendSiteCode\": \"" + ewbResult.getStr("sendSiteCode") + "\"\n" +
//                        "\t},\n" +
//                        "\t\"receiveInfo\": {\n" +
//                        "\t\t\"provinceCode\": \"" + ewbResult.getStr("dispatchProvinceCode") + "\",\n" +
//                        "\t\t\"cityCode\": \"" + ewbResult.getStr("dispatchCityCode") + "\",\n" +
//                        "\t\t\"districtCode\": \"" + ewbResult.getStr("dispatchDistrictCode") + "\",\n" +
//                        "\t\t\"townCode\": \"\",\n" +
//                        "\t\t\"customAreaCode\": \"" + ewbResult.getStr("dispatchCustomAreaCode") + "\",\n" +
//                        "\t\t\"dispatchStaff\": null,\n" +
//                        "\t\t\"receiveSiteCode\": \"" + ewbResult.getStr("dispatchSiteCode") + "\"\n" +
//                        "\t},\n" +
//                        "\t\"sendAddr\": \"\",\n" +
//                        "\t\"receiveAddr\": \"\"\n" +
//                        "}";
//                calcHttp.body(body);
//                HttpResponse calcResponse = calcHttp.execute();
//                JSONObject calcRes = JSONUtil.toBean(calcResponse.body(), JSONObject.class);
//
//                if (!calcRes.getBool("status")) {
//                    log.info("运单号:{}，获取结算交易信息失败", s);
//                }
//
//                JSONObject calcResult = calcRes.getJSONObject("result");
//                JSONArray orgCostItems = calcResult.getJSONArray("orgCostItems");
//
//                BigDecimal inAmount = BigDecimal.ZERO, outAmount = BigDecimal.ZERO;
//                for (Object object : orgCostItems) {
//                    JSONObject object1 = (JSONObject) object;
//                    inAmount = inAmount.add(object1.getBigDecimal("inSum"));
//                    outAmount = outAmount.add(object1.getBigDecimal("outSum"));
//                }
//                log.info("运单号:{}，        收入:    {}         支出:       {}", s, inAmount, outAmount);
//            } catch (Exception e) {
//                log.info("运单号:{}，统计出错，错误信息:{}", s, e.getMessage());
//                continue;
//            }

//            try {
//                HttpRequest ewbHttp = HttpUtil.createPost("https://qy-service.tms.ztocc.com/tms/ewb/getInfo");
//                ewbHttp.body("{\"ewbNo\":\"" + s + "\"}");
//                HttpResponse ewbResponse = ewbHttp.execute();
//                JSONObject ewbRes = JSONUtil.toBean(ewbResponse.body(), JSONObject.class);
//
//                if (!ewbRes.getBool("status")) {
//                    log.info("运单号:{}，接口获取失败", s);
//                    continue;
//                }
//
//                JSONObject ewbResult = ewbRes.getJSONObject("result");
//                if (ObjectUtil.isEmpty(ewbResult)) {
//                    log.info("运单号:{}，结果为空", s);
//                    continue;
//                }
//
//                BigDecimal calcWeight = ewbResult.getBigDecimal("weight");
//                if (ObjectUtil.isEmpty(calcWeight)) {
//                    log.info("运单号:{}，结算重量为空", s);
//                    continue;
//                }
//
//                log.info("运单号:{}，结算重量:{}", s, calcWeight);
//            } catch (Exception e) {
//                log.info("运单号:{}，获取运单结算重量出错，错误信息:{}", s, e.getMessage());
//                continue;
//            }
//        }
    }

    @lombok.Data
    public static class Data {
        private String waybillNo;
        private String sendSiteCode;
        private String sendSiteName;
    }
}
