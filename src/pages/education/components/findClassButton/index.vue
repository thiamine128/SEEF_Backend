<template>
    <div class="create-class-container">
        <button class="first-button" @click="showFindClassPage">查询班级</button>

        <div class="create-class-modal" v-if="showFindClassModal">
            <div class="modal-content">
                <span class="close-icon" @click="showFindClassModal = false">&times;</span>

                <h3>查询班级</h3>

                <form @submit.prevent="findClass">
                    <div class="input-group">
                        <input placeholder="请输入课程编号" id="courseId" v-model="courseId" min="1">
                    </div>

                    <div class="input-group">
                        <input placeholder="请输入课程名称" type="text" id="courseName" v-model="courseName">
                    </div>

                    <button style="width: 100%" type="submit">确认</button>
                </form>
            </div>
        </div>
    </div>
</template>

<script>
import {findClassAPI, findClassByNameAPI} from "@/pages/education/components/findClassButton/api/api";
import {getUserName} from "@/pages/education/api";
import {callError} from "@/callMessage";
export default {
    name: "findClassButton",
    data() {
        return {
            showFindClassModal: false,
            classes: null,
            courseId: null,
            courseName: null,
        };
    },

    methods: {
        showFindClassPage() {
            this.showFindClassModal = true;
        },

        async findClass() {
            if(this.courseId || this.courseName) {
                if(this.courseId !== null) {
                    this.classes = await findClassAPI(this.courseId);
                }
                else if(this.courseName !== null) {
                    this.classes = await findClassByNameAPI(this.courseName);
                    if(this.classes === -1) return ;
                }
                else return ;

                if(this.classes === null) return ;
                for(let i = 0; i < this.classes.length; i++) {
                    let tmp = [];
                    for(let j = 0; j < this.classes[i].teachers.length; j++) {
                        let nameT = await getUserName(this.classes[i].teachers[j]);
                        tmp.push(nameT);
                    }
                    this.classes[i].teachers = tmp;
                    if(this.classes[i].teachers.length === 0) {
                        this.classes[i].teachers = ['暂未分配教师'];
                    }
                    // console.log(this.classes[i].teachers);

                }
                // console.log(this.classes);
                this.$emit('update-classes', this.classes, this.courseId);
                this.$emit('update-show-course-list', false);
                this.$emit('update-show-class-list', true);
                this.showFindClassModal = false;
            }
            else callError('请至少填写一处');
        },
    },
}
</script>

<style scoped>
/*.create-class-container {*/
/*    display: flex;*/
/*    justify-content: center;*/
/*    align-items: center;*/
/*    margin: 20px;*/
/*}*/

/*.create-class-button {*/
/*    padding: 10px 20px;*/
/*    background-color: #007bff;*/
/*    color: white;*/
/*    border: none;*/
/*    cursor: pointer;*/
/*}*/

/*.create-class-modal {*/
/*    position: fixed;*/
/*    top: 50%;*/
/*    left: 50%;*/
/*    transform: translate(-50%, -50%);*/
/*    background-color: white;*/
/*    padding: 20px;*/
/*    border-radius: 5px;*/
/*    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);*/
/*    z-index: 1000;*/
/*}*/

/*.modal-content {*/
/*    display: flex;*/
/*    flex-direction: column;*/
/*    align-items: center;*/
/*}*/

/*.close-icon {*/
/*    position: absolute;*/
/*    top: 10px;*/
/*    right: 10px;*/
/*    font-size: 20px;*/
/*    cursor: pointer;*/
/*}*/

/*.input-group {*/
/*    display: flex;*/
/*    flex-direction: column;*/
/*    margin-bottom: 15px;*/
/*}*/

/*.input-group label {*/
/*    margin-bottom: 5px;*/
/*}*/

/*.input-group input {*/
/*    padding: 5px;*/
/*    border: 1px solid #ccc;*/
/*    border-radius: 3px;*/
/*}*/

/*.input-group input[type="number"] {*/
/*    width: 200px;*/
/*}*/

/*.first-button {*/
/*    padding: 10px 20px;*/
/*    background-color: #007bff;*/
/*    color: white;*/
/*    border: none;*/
/*    cursor: pointer;*/
/*    border-radius: 5px;*/
/*    box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);*/
/*    transition: background-color 0.3s ease;*/
/*    font-size: 20px;*/
/*}*/

/*.first-button:hover {*/
/*    background-color: #0056b3;*/
/*}*/

.create-class-container {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 180px;
    height: 60px;
}

.create-class-button {
    padding: 10px 20px;
    background-color: #007bff;
    color: white;
    border: none;
    cursor: pointer;
}

.create-class-modal {
    position: fixed;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
    background-color: white;
    padding: 20px;
    border-radius: 5px;
    box-shadow: 0 4px 8px 0 rgba(0, 0, 0, 0.2);
    z-index: 1000;
}

.modal-content {
    display: flex;
    flex-direction: column;
    align-items: center;
}

.close-icon {
    position: absolute;
    top: 10px;
    right: 10px;
    font-size: 20px;
    cursor: pointer;
}

.input-group {
    display: flex;
    flex-direction: column;
    margin-bottom: 15px;
    width: 200px;
}

.input-group label {
    margin-bottom: 5px;
}

.input-group input {
    padding: 5px;
    border: 1px solid #ccc;
    border-radius: 3px;
}

.input-group input[type="number"] {
    width: 80px;
}

.first-button {
    width: 100%;
    height: 100%;
    background-color: rgba(11, 11, 11, 0.02);
    border: none;
    cursor: pointer;
    /*box-shadow: 0 2px 5px rgba(0, 0, 0, 0.2);*/
    transition: background-color 0.3s ease;
    font-size: 16px;
    font-weight: bold;
    color: #2b2b2b;
}

.first-button:hover {
    background-color: rgba(55, 55, 55, 0.1);
}
</style>
