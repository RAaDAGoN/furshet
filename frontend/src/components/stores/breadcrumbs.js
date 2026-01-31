import { defineStore } from "pinia";

export const useBreadcrumbsStore = defineStore("breadcrumbs", {
    state: () => ({
        category: null,
        product: null,
    }),

    actions: {
        setCategory(category) {
            this.category = category;
            this.product = null;
        },

        setProduct(product) {
            this.product = product;
        },

        clear() {
            this.category = null;
            this.product = null;
        },
    },
});
