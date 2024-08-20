<template>
    <div class="course-image">
        <img :src="url" alt="Course image" v-if="url != null">
        <div class="cover" v-else :style="[
                { background: numberToColor(nameHash(courseName))},
                { width: '100%' },
                { height: '100%' },
                {display: 'flex'},
                {justifyContent: 'center'}
            ]">

            <el-text class="cover-title">
                {{ courseName }}
            </el-text>
        </div>
    </div>
</template>

<script>
import { numberToColor } from "../../util/color";
export default {
    props: {
        url: String,
        courseName: String
    },
    methods: {
        nameHash(str) {
            var hash = 0, i, chr;
            if (str == null) return 0;
            if (str == "") return hash;
            for (i = 0; i < str.length; i++) {
                chr   = str.charCodeAt(i);
                hash  = ((hash << 5) - hash) + chr;
                hash |= 0;
            }
            if (hash < 0) hash = -hash;
            return hash;
        },
        numberToColor
    }
}

</script>

<style>

.course-image {
    height: 150px;
}

.course-image img {
    width: 100%;
    height: 100%;
    object-fit: cover;
}


.cover-title {
    color: #fff;
    font-size: 30px;
    font-weight: bolder;
}

</style>
