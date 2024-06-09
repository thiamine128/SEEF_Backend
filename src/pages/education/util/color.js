 export function numberToColor(n) {
    const colorList = [
        "#4BBDEE",
        "#0d18ca",
        "#4BBDEE",
        "#13DAC2",
        "#2578f9",
        "#009ccd",
        "#57C946",
        "#1b6cc6",
        "#ff0242",
    ]
    return colorList[n % colorList.length];
}

export function numberToColor2(n) {
    const colorList = [
        "#ff1cab",
        "#13DAC2",
        "#26ffd5",
    ]
    return colorList[n % colorList.length];
}
