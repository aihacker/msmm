package com.d2956987215.mow.widgets.bean;

import java.util.List;

/**
 * Created by Administrator on 2016/5/17.
 */
public class ProvinceBean {


    /**
     * value : 2
     * text : 北京
     * children : [{"value":52,"text":"北京","children":[{"value":500,"text":"东城区","children":[]},{"value":501,"text":"西城区","children":[]},{"value":502,"text":"海淀区","children":[]},{"value":503,"text":"朝阳区","children":[]},{"value":504,"text":"崇文区","children":[]},{"value":505,"text":"宣武区","children":[]},{"value":506,"text":"丰台区","children":[]},{"value":507,"text":"石景山区","children":[]},{"value":508,"text":"房山区","children":[]},{"value":509,"text":"门头沟区","children":[]},{"value":510,"text":"通州区","children":[]},{"value":511,"text":"顺义区","children":[]},{"value":512,"text":"昌平区","children":[]},{"value":513,"text":"怀柔区","children":[]},{"value":514,"text":"平谷区","children":[]},{"value":515,"text":"大兴区","children":[]},{"value":516,"text":"密云县","children":[]},{"value":517,"text":"延庆县","children":[]}]}]
     */

    private int value;
    private String text;
    /**
     * value : 52
     * text : 北京
     * children : [{"value":500,"text":"东城区","children":[]},{"value":501,"text":"西城区","children":[]},{"value":502,"text":"海淀区","children":[]},{"value":503,"text":"朝阳区","children":[]},{"value":504,"text":"崇文区","children":[]},{"value":505,"text":"宣武区","children":[]},{"value":506,"text":"丰台区","children":[]},{"value":507,"text":"石景山区","children":[]},{"value":508,"text":"房山区","children":[]},{"value":509,"text":"门头沟区","children":[]},{"value":510,"text":"通州区","children":[]},{"value":511,"text":"顺义区","children":[]},{"value":512,"text":"昌平区","children":[]},{"value":513,"text":"怀柔区","children":[]},{"value":514,"text":"平谷区","children":[]},{"value":515,"text":"大兴区","children":[]},{"value":516,"text":"密云县","children":[]},{"value":517,"text":"延庆县","children":[]}]
     */

    private List<City> children;

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<City> getChildren() {
        return children;
    }

    public void setChildren(List<City> children) {
        this.children = children;
    }

    public static class City {
        private int value;
        private String text;
        /**
         * value : 500
         * text : 东城区
         * children : []
         */

        private List<District> children;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public List<District> getChildren() {
            return children;
        }

        public void setChildren(List<District> children) {
            this.children = children;
        }

        public static class District {
            private int value;
            private String text;
            private List<?> children;

            public int getValue() {
                return value;
            }

            public void setValue(int value) {
                this.value = value;
            }

            public String getText() {
                return text;
            }

            public void setText(String text) {
                this.text = text;
            }

            public List<?> getChildren() {
                return children;
            }

            public void setChildren(List<?> children) {
                this.children = children;
            }
        }
    }
}
