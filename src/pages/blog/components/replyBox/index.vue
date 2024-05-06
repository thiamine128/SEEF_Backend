<template>
    <div class="replySet">
        <div :class="{ nameFont: true }"> <strong>阮阳栋：</strong> 操作系统是计算机系统中软件与硬件联系的纽带，课程内容丰富，既包含操作系统的基础理论，又涉及实际操作系统的设计与实现。操作系统实验设计是操作系统课程实践环节的集中表现，旨在巩固学生理论课学习的概念和原理，同时培养学生的工程实践能力。一些国内外著名大学都非常重视操作系统的实验设计，例如麻省理工学院的Frans Kaashoek等设计的JOS和xv6教学操作系统、哈佛大学的David A. Holland 等设计的OS161 操作系统用于实现操作系统实验教学。

            我们尝试了MINIX、Nachos、Linux、Windows等操作系统实验，发现以Linux和Windows为基础的实验，由于系统规模庞大，很难让学生建立起完整的操作系统概念，导致专业知识碎片化，不符合系统能力培养目标。此外，操作系统涉及硬件的很多相关知识，本身还包含并发程序设计等比较难理解的概念，因此学生的学习曲线很陡峭，如何让学生由浅入深，平滑地掌握这些知识是实验设计的难点。本书设计实验的基本目标是：一学期内设计实现一个可在实际硬件平台上运行的小型操作系统，该系统具备现代操作系统特征（如虚存管理、多进程等），符合工业标准。 </div>

    </div>
</template>

<script>
export default {
    name: "replyBox",
    props: ['name', 'content'],
}
</script>

<style scoped>
.replySet{
    display: flex;
    flex-direction: row;
    width: 100%;
    min-height: 15px;
    margin-top: 3px;
    margin-bottom: 3px;
}
.nameFont{
    font-family: '微软雅黑', 'Microsoft YaHei', sans-serif;
    font-size: 14px;
    text-align: left;
    margin-left: 10px;
}
</style>
