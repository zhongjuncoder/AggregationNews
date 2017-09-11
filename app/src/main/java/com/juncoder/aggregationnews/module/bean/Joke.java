package com.juncoder.aggregationnews.module.bean;

import java.util.List;

/**
 * Created by zhongjuncoder on 2017/9/11.
 * email:1247660633@qq.com
 * <p>
 * * error_code : 0
 * reason : Success
 */

public class Joke {

    private int error_code;
    private String reason;
    private ResultBean result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private List<DataBean> data;

        public List<DataBean> getData() {
            return data;
        }

        public void setData(List<DataBean> data) {
            this.data = data;
        }

        /**
         * content : 女生分手的原因有两个，
         * 一个是：闺蜜看不上。另一个是：闺蜜看上了。
         * hashId : 607ce18b4bed0d7b0012b66ed201fb08
         * unixtime : 1418815439
         * updatetime : 2014-12-17 19:23:59
         */
        public static class DataBean {

            private String content;
            private String hashId;
            private int unixtime;
            private String updatetime;

            public String getContent() {
                return content;
            }

            public void setContent(String content) {
                this.content = content;
            }

            public String getHashId() {
                return hashId;
            }

            public void setHashId(String hashId) {
                this.hashId = hashId;
            }

            public int getUnixtime() {
                return unixtime;
            }

            public void setUnixtime(int unixtime) {
                this.unixtime = unixtime;
            }

            public String getUpdatetime() {
                return updatetime;
            }

            public void setUpdatetime(String updatetime) {
                this.updatetime = updatetime;
            }
        }
    }
}
