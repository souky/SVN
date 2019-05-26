<template>
  <div class="recording-container">
    <!-- Tab 部分 -->
    <ul class="tab-bor">
      <li class="poi">直播</li>
      <li class="poi active"><router-link to="/recording">录制</router-link></li>
      <li class="poi"><router-link to="/play">录制</router-link></li>
    </ul>

    <div class="main-container">
      <el-row type="flex" class="row-bg mt20" justify="end">
        <el-col :span="4" class="w250 mr20">
          <label class="mr20">学校</label>
          <el-select class="w180" placeholder="请选择学校">
            <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4" class="w250 mr20">
          <label class="mr20">年级</label>
          <el-select class="w180" placeholder="请选择年级">
            <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4" class="w250 mr20">
          <label class="mr20">教师</label>
          <el-select class="w180" placeholder="请选择教师">
            <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
            </el-option>
          </el-select>
        </el-col>
        <el-col :span="4" class="w250">
          <label class="mr20">学科</label>
          <el-select class="w180" placeholder="请选择学科">
            <el-option
                    v-for="item in options"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
            </el-option>
          </el-select>
        </el-col>
      </el-row>

      <el-row type="flex" class="row-bg mt20" justify="end">
        <!-- 节目名 -->
        <el-col :span="4" class="w250" style="margin-right: 35px;">
          <label class="mr20">节目名</label>
          <el-input class="w180" placeholder="请输入节目名"></el-input>
        </el-col>
        <!-- 开始 -->
        <el-col :span="4" class="w250 mr20">
          <label class="mr20">开始</label>
          <el-date-picker
                  type="datetime"
                  class="w180"
                  placeholder="选择日期时间">
          </el-date-picker>
        </el-col>
        <!-- 结束 -->
        <el-col :span="4" class="w250 mr20">
          <label class="mr20">结束</label>
          <el-date-picker
                  type="datetime"
                  placeholder="选择日期时间">
          </el-date-picker>
        </el-col>
        <!-- 查询 -->
        <el-col :span="4" class="w250">
          <el-button type="primary" class="w180" style="float: right;margin-right: 16px;">查询</el-button>
        </el-col>
      </el-row>

      <!--表格部分-->
      <div class="table-striped mt20">
        <table class="ktable">
          <thead>
          <tr>
            <th>学校</th>
            <th>年级</th>
            <th>录制源</th>
            <th>播放机构</th>
            <th>学科</th>
            <th>教师</th>
            <th>节目名</th>
            <th>开始时间</th>
            <th>结束时间</th>
            <th>状态</th>
            <th>操作</th>
          </tr>
          </thead>
          <tbody>
          <tr class="tc">
            <td>第一小学</td>
            <td>二年</td>
            <td>第一小学</td>
            <td>音乐</td>
            <td>英语</td>
            <td>帅帅哥</td>
            <td>节目名</td>
            <td>2017/09/08  09:48</td>
            <td>2017/09/08  09:48</td>
            <td>进行中</td>
            <td>
              <i class="fa fa-exclamation-circle fa-lg" ></i>
              <i class="fa fa-search-plus fa-lg"></i>
              <i class="fa fa-pencil-square-o fa-lg" ></i>
              <i class="fa fa-trash fa-lg" ></i>
            </td>
          </tr>
          <tr class="tc">
            <td>第一小学</td>
            <td>二年</td>
            <td>第一小学</td>
            <td>音乐</td>
            <td>英语</td>
            <td>帅帅哥</td>
            <td>节目名</td>
            <td>2017/09/08  09:48</td>
            <td>2017/09/08  09:48</td>
            <td>进行中</td>
            <td>
              <i class="fa fa-exclamation-circle fa-lg" ></i>
              <i class="fa fa-search-plus fa-lg"></i>
              <i class="fa fa-pencil-square-o fa-lg" ></i>
              <i class="fa fa-trash fa-lg" ></i>
            </td>
          </tr>
          <tr class="tc">
            <td>第一小学</td>
            <td>二年</td>
            <td>第一小学</td>
            <td>音乐</td>
            <td>英语</td>
            <td>帅帅哥</td>
            <td>节目名</td>
            <td>2017/09/08  09:48</td>
            <td>2017/09/08  09:48</td>
            <td>进行中</td>
            <td>
              <i class="fa fa-exclamation-circle fa-lg" ></i>
              <i class="fa fa-search-plus fa-lg"></i>
              <i class="fa fa-pencil-square-o fa-lg" ></i>
              <i class="fa fa-trash fa-lg" ></i>
            </td>
          </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script>
  export default{
    data () {
      return {
        msg:'hello vue',
        options: [{
          value: '选项1',
          label: '黄金糕'
        }, {
          value: '选项2',
          label: '双皮奶'
        }, {
          value: '选项3',
          label: '蚵仔煎'
        }, {
          value: '选项4',
          label: '龙须面'
        }, {
          value: '选项5',
          label: '北京烤鸭'
        }],
      }
    },

  }
</script>

<style scoped="scoped">
  .el-input__icon {
    width: 55px;
  }
  .el-date-editor.el-input {
    width: 180px;
  }
</style>