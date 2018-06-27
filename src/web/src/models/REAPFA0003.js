import feedback from '../utils/feedback'
import { create, update, query, remove } from '../apis/application'
import { DEFAULT_PAGE_NUMBER, DEFAULT_PAGE_SIZE } from '../constants'

const { notification: { error } } = feedback


function specification (state) {
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
      owner: null,
    },
    showCreateModal: false,
    page: {
      size: DEFAULT_PAGE_SIZE,
      number: DEFAULT_PAGE_NUMBER,
    },
  },
  effects: {
    * query ({ page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE }, { call, put, select }) {
      const state = yield select(({ REAPFA0003 }) => (REAPFA0003))
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
    * update ({ data }, { call, put, select }) {
      const state = yield select(({ REAPFA0003 }) => (REAPFA0003))
      const result = yield call(update, data)
      if (result.success) {
        yield put({
          type: 'query', ...specification(state),
        })
      } else {
        error(result)
      }
    },
    * delete ({ id }, { call, put, select }) {
      const result = yield call(remove, id)
      if (result.success) {
        const state = yield select(({ REAPFA0003 }) => (REAPFA0003))
        yield put({ type: 'query', ...specification(state) })
      } else {
        error(result)
      }
    },
    * create ({ data, form }, { call, select, put }) {
      const state = yield select(({ REAPFA0003 }) => (REAPFA0003))
      const result = yield call(create, data)
      if (result.success) {
        form.resetFields()
        yield put({ type: 'setState', showCreateModal: false })
        yield put({ type: 'query', ...specification(state) })
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
        if (location.pathname === '/REAPFA0003') {
          dispatch({ type: 'query', page: DEFAULT_PAGE_NUMBER, size: DEFAULT_PAGE_SIZE })
        }
      })
    },
  },
}
