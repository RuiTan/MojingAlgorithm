
let base_url = "http://47.102.43.156:8018/"
// let base_url = "http://localhost:8008/"

$(document).ready(function () {
    "use strict";

    // pre，recall，acc
    $.ajax({
        url: base_url + 'metrics?modelId=2',
        success: function (d) {
            console.log(d)

            let borderColors = [
                'rgb(179,66,74,0.8)',
                'rgb(140,83,27,0.8)',
                'rgb(118,145,73,0.8)',
                'rgb(88,94,170,0.8)',
                'rgb(199,105,104,0.8)',
                'rgb(130,104,88,0.8)',
                'rgb(109,131,70,0.8)',
                'rgb(73,78,143,0.8)',
                'rgb(187,80,93,0.8)',
                'rgb(174,102,66,0.8)',
            ]
            let backgroundColors = [
                'rgb(179,66,74,0.2)',
                'rgb(140,83,27,0.2)',
                'rgb(118,145,73,0.2)',
                'rgb(88,94,170,0.2)',
                'rgb(199,105,104,0.2)',
                'rgb(130,104,88,0.2)',
                'rgb(109,131,70,0.2)',
                'rgb(73,78,143,0.2)',
                'rgb(187,80,93,0.2)',
                'rgb(174,102,66,0.2)',
            ]

            let data = d['data']
            // console.log(data)
            let labels = data['dates'][0]
            labels.forEach(
                (elem, index, labels) => {
                    labels[index] = elem.substring(0, 10)
                }
            )
            let legends = data['ids'][0]
            legends.forEach(
                (elem, index, l) => {
                    l[index] = 'Task ' + elem + ', ' + labels[index]
                }
            )
            let oa = data['oa'][0]
            let pre = data['pre'][0]
            let recall = data['recall'][0]
            let f1 = pre.slice()
            f1.forEach((elem, index, f1) => {
                f1[index] = 2 * (pre[index] * recall[index] / (pre[index] + recall[index]))
            })
            let ctx1 = document.getElementById('modelChart1');
            new Chart(ctx1, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        data: oa,
                        borderColor: borderColors[0],
                        backgroundColors: backgroundColors[0],
                        borderWidth: 1,
                        label: 'Overall Accuracy',
                        fill: false
                    }, {
                        data: pre,
                        borderColor: borderColors[3],
                        backgroundColors: backgroundColors[3],
                        borderWidth: 1,
                        label: 'Precision',
                        fill: false
                    }, {
                        data: recall,
                        borderColor: borderColors[6],
                        backgroundColors: backgroundColors[6],
                        borderWidth: 1,
                        label: 'Recall',
                        fill: false
                    }, {
                        data: f1,
                        borderColor: borderColors[8],
                        backgroundColors: backgroundColors[8],
                        borderWidth: 1,
                        label: 'F1 Score',
                        fill: false
                    }]
                },
                options: {
                    legend: {
                        display: true
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: false,
                                fontSize: 10,
                                max: 1,
                                min: 0.75
                            }
                        }],
                        xAxes: [{
                            ticks: {
                                beginAtZero: true,
                                fontSize: 10
                            }
                        }]
                    }
                }
            });

            let mse = data['mse'][0]
            let ctx2 = document.getElementById('mseChart');
            new Chart(ctx2, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        data: mse,
                        borderColor: borderColors[0],
                        backgroundColors: backgroundColors[0],
                        borderWidth: 1,
                        label: 'Mean Squared Error',
                        fill: false,
                        lineTension:0.1
                    }]
                },
                options: {
                    legend: {
                        display: true
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: false,
                                fontSize: 10,
                                max: 130,
                                min: 100
                            }
                        }],
                        xAxes: [{
                            ticks: {
                                beginAtZero: true,
                                fontSize: 10
                            }
                        }]
                    }
                }
            });

            let size = 10
            let date = labels.slice(-size)
            let tpr = data['tpr'][0].slice(-size)
            let fpr = data['fpr'][0][0]
            let ctx3 = document.getElementById('rocChart');
            let datasets = []
            for (let i = 0; i < size; i++) {
                datasets.push({
                    data: tpr[i],
                    borderColor: borderColors[i],
                    borderWidth: 1,
                    label: date[i],
                    fill: true,
                    pointBackgroundColor:borderColors[i],
                    pointBorderColor:"#fff",
                    pointHoverBackgroundColor:"#fff",
                    pointHoverBorderColor:borderColors[i]
                })
            }
            new Chart(ctx3, {
                type: 'line',
                data: {
                    labels: fpr,
                    datasets: datasets
                },
                options: {
                    legend: {
                        display: true
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: false,
                                fontSize: 10,
                                max: 1,
                                min: 0
                            }
                        }],
                        xAxes: [{
                            ticks: {
                                beginAtZero: true,
                                fontSize: 10
                            }
                        }]
                    }
                }
            });

            size = 10;
            datasets = []
            date = labels.slice(-size)
            let c0 = data['c0Rate'][0].slice(-size)
            let classes = ['最不推荐（0-59）', '中等推荐（60-69）', '比较推荐（70-79）', '最推荐（80-100）']
            let c1 = data['c1Rate'][0].slice(-size)
            let c2 = data['c2Rate'][0].slice(-size)
            let c3 = data['c3Rate'][0].slice(-size)
            let ctx4 = document.getElementById('fourClasses');
            for (let i = 0; i < size; i++) {
                datasets.push({
                    data: [c0[i], c1[i], c2[i], c3[i]],
                    label: date[i],
                    borderColor: borderColors[i],
                    backgroundColor: backgroundColors[i],
                    borderWidth: 1,
                    pointBackgroundColor:borderColors[i],
                    pointBorderColor:"#fff",
                    pointHoverBackgroundColor:"#fff",
                    pointHoverBorderColor:borderColors[i]
                })
            }
            new Chart(ctx4, {
                type: 'radar',
                data: {
                    labels: classes,
                    datasets: datasets
                },
                options: {
                }
            })

            let kaf = data['kaf'][0]
            let p = data['p'][0]
            let ctx5 = document.getElementById('kafChart')
            new Chart(ctx5, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        data: kaf,
                        borderColor: borderColors[0],
                        backgroundColors: backgroundColors[0],
                        borderWidth: 1,
                        label: '卡方值',
                        fill: false,
                        lineTension:0.1
                    }, {
                        data: p,
                        borderColor: borderColors[3],
                        backgroundColors: backgroundColors[3],
                        borderWidth: 1,
                        label: 'P值',
                        fill: false,
                        lineTension:0.1
                    }]
                },
                options: {
                    legend: {
                        display: true
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: false,
                                fontSize: 10,
                                max: 1,
                                min: 0
                            }
                        }],
                        xAxes: [{
                            ticks: {
                                beginAtZero: true,
                                fontSize: 10
                            }
                        }]
                    }
                }
            })

            let designerRate = data['designerRate'][0]
            let interRate = data['interRate'][0]
            let intraRate = data['intraRate'][0]
            let ctx6 = document.getElementById('designerReliable')
            new Chart(ctx6, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        data: designerRate,
                        borderColor: borderColors[1],
                        backgroundColors: backgroundColors[1],
                        borderWidth: 1,
                        label: '设计师认可度',
                        fill: false,
                        lineTension:0.1
                    }, {
                        data: interRate,
                        borderColor: borderColors[4],
                        backgroundColors: backgroundColors[4],
                        borderWidth: 1,
                        label: '评分者间信度',
                        fill: false,
                        lineTension:0.1
                    }, {
                        data: intraRate,
                        borderColor: borderColors[7],
                        backgroundColors: backgroundColors[7],
                        borderWidth: 1,
                        label: '评分者内部信度',
                        fill: false,
                        lineTension:0.1
                    }]
                },
                options: {
                    legend: {
                        display: true
                    },
                    scales: {
                        yAxes: [{
                            ticks: {
                                beginAtZero: false,
                                fontSize: 10,
                                max: 0.9,
                                min: 0.5
                            }
                        }],
                        xAxes: [{
                            ticks: {
                                beginAtZero: true,
                                fontSize: 10
                            }
                        }]
                    }
                }
            })
        }
    })

    $.ajax({
        url: base_url + 'job?modelId=2',
        success: function (d) {
            let data = d['data'];
            for (let i= data.length-1;i>=0;i--){
                let elem = data[i];
                let id = elem['id']
                let startTime = elem['startTime']
                let endTime = elem['endTime'] == null ? "" : elem['endTime']
                let status = elem['status']
                let html = $('<tr>\n' +
                    '                    <td>'+id+'</td>\n' +
                    '                    <td><span class="text-muted">'+startTime+'</span></td>\n' +
                    '                    <td><span class="text-muted">'+endTime+'</span></td>\n' +
                    '                  </tr>')
                if (status === 0) {
                    html.append($('<td><span class="label label-danger">正在训练</span></td>\n'))
                } else if (status === 1) {
                    html.append($('<td><span class="label label-success">训练结束</span></td>\n'))
                } else {
                    html.append($('<td><span class="label label-warning">已取消</span></td>\n'))
                }
                $('#training_history').prepend(html)
            }
            $('#history').DataTable();
        }
    })

    let categoryChart, labelChart;
    categoryChart = new Chart(document.getElementById('datasetCategories'), {
        type: 'pie',
        data: {
            datasets: [{
                data: [],
                backgroundColor: [],
            }],
            labels: []
        },
        options: {
            responsive: true,
            legend: {
                display: true,
                labels: {
                    display: false
                }
            },
            animation: {
                animateScale: true,
                animateRotate: true
            }
        }
    })
    labelChart = new Chart(document.getElementById('datasetLabels'), {
        type: 'bar',
        data: {
            labels: [],
            datasets: [{
                label: '数量（件）',
                data: [],
                backgroundColor: '#1ecab8'
            }]
        },
        options: {
            legend: {
                display: false,
                labels: {
                    display: true
                }
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontSize: 10,
                    },
                }],
                xAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontSize: 11
                    },
                    display: true
                }]
            }
        }
    })
    let file = $('#dataset')
    file.fileinput({
        language: 'zh',
        uploadUrl: base_url + 'uploadDataset', //上传的地址
        allowedFileExtensions : ['zip', 'tar.gz', 'rar'],//接收的文件后缀
        showUpload: true, //是否显示上传按钮
        showCaption: true,//是否显示标题
        browseClass: "btn btn-primary",
        maxFileSize: 0
    }).on('fileuploaded', function (event, data, previewId, index) {
        let colors = [
            '#b3424a', '#8c531b', '#769149', '#585eaa', '#c76968',
            '#826858', '#6d8346', '#494e8f', '#bb505d', '#ae6642'
        ]
        let result = data.response.data;
        let categories_data = result[0][0]['categories']
        let labels_data = result[0][0]['labels']
        let categories_count = categories_data.length
        categories_data = categories_data.substring(1, categories_count-1).split(",");
        let labels_count = labels_data.length
        labels_data = labels_data.substring(1, labels_count-1).split(",");
        let categories = result[1][0]
        let labels = result[1][1]
        console.log(categories_data)
        console.log(labels_data)
        console.log(categories)
        console.log(labels)
        let categories_colors = colors.splice(0, categories_data.length)
        console.log(categories_colors)
        $('#datasetAnalysis')[0].removeAttribute('hidden')
        categoryChart.data.datasets[0].data = categories_data;
        categoryChart.data.datasets[0].backgroundColor = categories_colors;
        categoryChart.data.labels = categories
        labelChart.data.datasets[0].data = labels_data;
        labelChart.data.labels = labels;
        categoryChart.update()
        labelChart.update()
    })

    let ctx2 = document.getElementById('modelChart2');
    let modelChart2 = new Chart(ctx2, {
        type: 'pie',
        data: {
            datasets: [{
                data: [
                ],
                backgroundColor: [
                ],

            }],
            labels: []
        },
        options: {
            responsive: true,
            legend: {
                display: false,
                labels: {
                    display: false
                }
            },
            animation: {
                animateScale: true,
                animateRotate: true
            }
        }
    });
    $.ajax({
        url: base_url + 'labelCount?tag=2',
        success: function (d) {
            let result = d['data'];
            let prefix = ['print（图案）',
                'floral（花的）',
                'lace（蕾丝）',
                'knit（针织）',
                'sleeve（袖子）',
                'maxi（马克西）',
                'shirt（衬衫）',
                'denim（牛仔布）',
                'striped（有条纹的）',
                'chiffon（薄纱）',
                'crochet（钩针编织）',
                'stripe（条纹）',
                'sleeveless（无袖的）',
                'summer（夏天）',
                'floral print（碎花）',
                'fit（合身）',
                'cotton（棉布）',
                'leather（皮革）',
                'faux（假的）',
                'classic（经典的）',
                'graphic（形象的）',
                'pleated（打褶的）',
                'red（红色的）',
                'long sleeve（长袖）',
                'embroidered（绣花）',
                'pink（粉色的）',
                'printed（打印）',
                'bodycon（身体康）',
                'crop（庄稼）',
                'skater（溜冰者）',
                'mini（小型的）',
                'love（爱）',
                'abstract（抽象的）',
                'muscle（肌肉）',
                'v-neck（V领）',
                'collar（衣领）',
                'skinny（瘦骨嶙峋的）',
                'pocket（口袋）',
                'mesh（网）',
                'button（按钮）',
                'shift（转移）',
                'trim（修剪）',
                'wash（洗）',
                'flare（耀斑）',
                'tribal（部落的）',
                'woven（编织）',
                'midi（迷笛）',
                'drawstring（拉绳）',
                'strapless（无肩带）',
                'sheer（纯粹的）',
                'faux leather（仿造皮）',
                'cami（卡米）',
                'cut（切）',
                'hooded（连帽的）',
                'rose（玫瑰）',
                'dot（点）',
                'textured（有质感的）',
                'boxy（四四方方的）',
                'cropped（裁剪）',
                'pencil（铅笔）',
                'distressed（苦恼）',
                'belted（系腰带）',
                'paisley（佩斯利）',
                'plaid（格子）',
                'cutout（剪下）',
                'slim（瘦）',
                'polka dot（波尔卡圆点）',
                'crepe（绉）',
                'hem（下摆）',
                'shopping（购物）',
                'pattern（图案）',
                'zip（压缩）',
                'a-line（一线）',
                'stretch（拉紧）',
                'sheath（鞘）',
                'longline（延绳）',
                'linen（亚麻布）',
                'ribbed（罗纹）',
                'racerback（赛车手）',
                'party（派对）',
                'marled（斑驳的）',
                'shoulder（肩膀）',
                'loose（松动的）',
                'beaded（串珠）',
                'crew（全体人员）',
                'colorblock（色块）',
                'cute（可爱的）',
                'light（光）',
                'dye（染料）',
                'heathered（杂色的）',
                'surplice（袈裟）',
                'tunic（束腰外衣）',
                'girls（女孩们）',
                'fringe（边缘）',
                'panel（控制板）',
                'french（法语）',
                'wrap（裹）',
                'contrast（对比）',
                'peasant（农民）',
                'floral lace（花卉蕾丝）',
                'chambray（青年布）',
                'oversized（超大号）',
                'abstract print（抽象印刷）',
                'raglan（插肩）',
                'scoop（舀）',
                'boho（放荡不羁的）',
                'acid（酸）',
                'chic（别致）',
                'ruffle（褶边）',
                'slit（狭缝）',
                'fur（毛皮）',
                'metallic（金属的）',
                'dolman（多尔曼）',
                'zipper（拉链）',
                'boyfriend（男朋友）'];
            // let labels = result['label'][0].slice(0,50);
            let labels = prefix.slice(0,50)
            console.log("prefix label：", labels)
            let counts = result['count'][0].slice(0,50);
            let colors = [];
            let alpha = 1.0;
            for (let i = 0; i < counts.length; i++){
                colors[i] = 'rgba(' + (Math.floor(Math.random() * 256)) + ',' + (Math.floor(Math.random() * 256)) + ',' + (Math.floor(Math.random() * 256)) + ', 0.3)';
                // colors[i] = 'rgba(218,112,214,'+alpha+')';
                alpha -= 0.01;
            }
            console.log(labels);
            console.log(counts);
            modelChart2.data.labels = labels;
            modelChart2.data.datasets[0].data = counts;
            modelChart2.data.datasets[0].backgroundColor = colors;
            modelChart2.update();
        }
    })
    let ctx3 = document.getElementById('modelChart3');
    let modelChart3 = new Chart(ctx3, {
        type: 'bar',
        data: {
            labels: [
            ],
            datasets: [{
                label: '数量（件）',
                data: [
                ],
                backgroundColor: '#1ecab8'
            }]
        },
        options: {
            legend: {
                display: false,
                labels: {
                    display: false
                }
            },
            scales: {
                yAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontSize: 10
                    },
                }],
                xAxes: [{
                    ticks: {
                        beginAtZero: true,
                        fontSize: 11
                    },
                    display: false
                }]
            }
        }
    });
    $.ajax({
        url: base_url + 'labelCount?tag=1',
        success: function (d) {
            let result = d['data'];
            let prefix = ['print（图案）',
                'floral（花的）',
                'lace（蕾丝）',
                'knit（针织）',
                'sleeve（袖子）',
                'maxi（马克西）',
                'shirt（衬衫）',
                'denim（牛仔布）',
                'striped（有条纹的）',
                'chiffon（薄纱）',
                'crochet（钩针编织）',
                'stripe（条纹）',
                'sleeveless（无袖的）',
                'summer（夏天）',
                'floral print（碎花）',
                'fit（合身）',
                'cotton（棉布）',
                'leather（皮革）',
                'faux（假的）',
                'classic（经典的）',
                'graphic（形象的）',
                'pleated（打褶的）',
                'red（红色的）',
                'long sleeve（长袖）',
                'embroidered（绣花）',
                'pink（粉色的）',
                'printed（打印）',
                'bodycon（身体康）',
                'crop（庄稼）',
                'skater（溜冰者）',
                'mini（小型的）',
                'love（爱）',
                'abstract（抽象的）',
                'muscle（肌肉）',
                'v-neck（V领）',
                'collar（衣领）',
                'skinny（瘦骨嶙峋的）',
                'pocket（口袋）',
                'mesh（网）',
                'button（按钮）',
                'shift（转移）',
                'trim（修剪）',
                'wash（洗）',
                'flare（耀斑）',
                'tribal（部落的）',
                'woven（编织）',
                'midi（迷笛）',
                'drawstring（拉绳）',
                'strapless（无肩带）',
                'sheer（纯粹的）',
                'faux leather（仿造皮）',
                'cami（卡米）',
                'cut（切）',
                'hooded（连帽的）',
                'rose（玫瑰）',
                'dot（点）',
                'textured（有质感的）',
                'boxy（四四方方的）',
                'cropped（裁剪）',
                'pencil（铅笔）',
                'distressed（苦恼）',
                'belted（系腰带）',
                'paisley（佩斯利）',
                'plaid（格子）',
                'cutout（剪下）',
                'slim（瘦）',
                'polka dot（波尔卡圆点）',
                'crepe（绉）',
                'hem（下摆）',
                'shopping（购物）',
                'pattern（图案）',
                'zip（压缩）',
                'a-line（一线）',
                'stretch（拉紧）',
                'sheath（鞘）',
                'longline（延绳）',
                'linen（亚麻布）',
                'ribbed（罗纹）',
                'racerback（赛车手）',
                'party（派对）',
                'marled（斑驳的）',
                'shoulder（肩膀）',
                'loose（松动的）',
                'beaded（串珠）',
                'crew（全体人员）',
                'colorblock（色块）',
                'cute（可爱的）',
                'light（光）',
                'dye（染料）',
                'heathered（杂色的）',
                'surplice（袈裟）',
                'tunic（束腰外衣）',
                'girls（女孩们）',
                'fringe（边缘）',
                'panel（控制板）',
                'french（法语）',
                'wrap（裹）',
                'contrast（对比）',
                'peasant（农民）',
                'floral lace（花卉蕾丝）',
                'chambray（青年布）',
                'oversized（超大号）',
                'abstract print（抽象印刷）',
                'raglan（插肩）',
                'scoop（舀）',
                'boho（放荡不羁的）',
                'acid（酸）',
                'chic（别致）',
                'ruffle（褶边）',
                'slit（狭缝）',
                'fur（毛皮）',
                'metallic（金属的）',
                'dolman（多尔曼）',
                'zipper（拉链）',
                'boyfriend（男朋友）'];
            let labels = prefix.slice(0, 100);
            // let labels = result['label'][0].slice(0, 100);
            let counts = result['count'][0].slice(0, 100);
            console.log(labels);
            console.log(counts);
            modelChart3.data.labels = labels;
            modelChart3.data.datasets[0].data = counts;
            modelChart3.update();
        }
    })
    // $(".upload").on("click", function () {
    //     $.toast({
    //         heading: '文件上传提醒',
    //         text: '训练集“1.zip”正在上传中',
    //         position: 'top-right',
    //         loaderBg: '#ff6849',
    //         icon: 'info',
    //         hideAfter: 3000,
    //         stack: 6
    //     });
    //
    //     setTimeout(function () {
    //         $.toast({
    //             heading: '文件上传提醒',
    //             text: '训练集“1.zip”上传成功',
    //             position: 'top-right',
    //             loaderBg: '#ff6849',
    //             icon: 'success',
    //             hideAfter: 3500,
    //             stack: 6
    //         })
    //     }, 5000)
    //
    // });

})

function newJob(modelId) {
    $.ajax({
        url: base_url + 'run?modelId=2',
        method: 'post',
        success: function (d) {
            let elem = d['data'];
            let id = elem['id']
            let startTime = elem['startTime']
            let endTime = elem['endTime'] == null ? "" : elem['endTime']
            let status = elem['status']
            let html = $('<tr>\n' +
                '                    <td>'+id+'</td>\n' +
                '                    <td><span class="text-muted">'+startTime+'</span></td>\n' +
                '                    <td><span class="text-muted">'+endTime+'</span></td>\n' +
                '                  </tr>')
            if (status === 0) {
                html.append($('<td><span class="label label-danger">正在训练</span></td>\n'))
            } else if (status === 1) {
                html.append($('<td><span class="label label-success">训练结束</span></td>\n'))
            } else {
                html.append($('<td><span class="label label-warning">已取消</span></td>\n'))
            }
            $('#training_history').prepend(html)
            $.toast({
                heading: '任务提醒',
                text: '任务'+id+'提交成功，预计训练时间2小时',
                position: 'top-right',
                loaderBg: '#ff6849',
                icon: 'info',
                hideAfter: 3000,
                stack: 6
            });
        }
    })
}