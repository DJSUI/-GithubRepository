module.exports = {
  root: true,
  env: {
    node: true,
  },
  extends: [
    'standard',
    'plugin:vue/vue3-essential'
  ],
  overrides: [
    {
      env: {
        node: true
      },
      files: [

        '.eslintrc.{cjs,js}'
      ],
      parserOptions: {
        sourceType: 'script'
      }
    }
  ],
  parserOptions: {
    ecmaVersion: 'latest',
    sourceType: 'module',
    parser: 'babel-eslint',
  },
  plugins: [
    'vue'
  ],
  rules: {
    'vue/max-attributes-per-line': ['error', {
      singleline: 1,
      multiline: {
        max: 1, // 多行最多一个属性
        allowFirstLine: true // 允许第一行有属性
      }
    }],
    // 在闭合标签括号前换行
    'vue/html-closing-bracket-newline': ['error', {
      singleline: 'never',
      multiline: 'always'
    }]
  }
}
