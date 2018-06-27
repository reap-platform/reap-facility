import feedback from '../utils/feedback'
import { create, update, query, remove } from '../apis/config'
import { queryAll } from '../apis/application'
import { DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE } from '../constants'

const { notification: { error } } = feedback

function functionSpec (state) {
  return {
    size: state.page && state.page.size,
    page: state.page && state.page.number,
  }
}

export default {

  state: {
    search: {
      name: null,
      systemCode: null,
      code: null,
    },
    showCreateModal: false,
    page: {
      size: DEFAULT_PAGE_SIZE,
      number: DEFAULT_PAGE_NUMBER,
    },
    applications: [],
  },
  effects: {
    * queryApplications (action, { call, put }) {
      const result = yield call(queryAll)
      if (result.success) {
        yield put({ type: 'setState', applications: result.payload })
      } else {
        error(result)
      }
    },
    * query ({ page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE }, { call, put, select }) {
      const state = yield select(({ REAPFA0001 }) => (REAPFA0001))
      const params = {
        size, page, ...state.search,
      }
      const result = yield call(query, params)
      if (result.success) {
        yield put({ type: 'setState', page: result.payload })
      } else {
        error(result)
      }
    },
    * update ({ func }, { call, put, select }) {
      const state = yield select(({ REAPFA0001 }) => (REAPFA0001))
      const result = yield call(update, func)
      if (result.success) {
        yield put({
          type: 'query', ...functionSpec(state),
        })
      } else {
        error(result)
      }
    },
    * delete ({ id }, { call, put, select }) {
      const result = yield call(remove, id)
      if (result.success) {
        const state = yield select(({ REAPFA0001 }) => (REAPFA0001))
        yield put({ type: 'query', ...functionSpec(state) })
      } else {
        error(result)
      }
    },
    * create ({ func, form }, { call, select, put }) {
      const state = yield select(({ REAPFA0001 }) => (REAPFA0001))
      const result = yield call(create, func)
      if (result.success) {
        form.resetFields()
        yield put({ type: 'setState', showCreateModal: false })
        yield put({ type: 'query', ...functionSpec(state) })
      } else {
        error(result)
      }
    },
  },
  reducers: {
    setState (state, newState) {
      return {
        ...state,
        ...newState,
        search: {
          ...state.search,
          ...newState.search,
        },
      }
    },
  },
  subscriptions: {
    setup ({ dispatch, history }) {
      history.listen((location) => {
        if (location.pathname === '/REAPFA0001') {
          dispatch({ type: 'query', page: DEFAULT_PAGE_NUMBER, size: DEFAULT_PAGE_SIZE })
          dispatch({ type: 'queryApplications' })
        }
      })
    },
  },
}
