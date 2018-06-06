import React from 'react'
import { Row, Col, Table, Popconfirm, Input, Form, Button } from 'antd'
import EditableCell from '../EditableCell'
import FunctionForm from './RouteForm'
import styles from './RouteList.less'

const Component = ({
  page, dispatch, showCreateModal, loading,
}) => {
  const { Item } = Form
  const onCellChange = (key, dataIndex) => {
    return (value) => {
      const update = page.content.find(o => o.id === key)
      update[dataIndex] = value
      dispatch({ type: 'REAPFA0002/update', func: update })
    }
  }
  const columns = [
    {
      title: '路由名称',
      dataIndex: 'name',
      width: '15%',
      key: 'name',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'name')}
        />
      ),
    },
    {
      title: '路由规则',
      dataIndex: 'path',
      width: '25%',
      key: 'path',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'path')}
        />
      ),
    },
    {
      title: '服务ID(serviceId)',
      dataIndex: 'serviceId',
      width: '25%',
      key: 'serviceId',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'serviceId')}
        />
      ),
    },
    {
      title: '转发地址(默认通过 serviceId 查找服务地址)',
      dataIndex: 'url',
      width: '30%',
      key: 'url',
      render: (text, record) => (
        <EditableCell
          value={text}
          onChange={onCellChange(record.id, 'url')}
        />
      ),
    },
    {
      title: '操作',
      width: '10%',
      dataIndex: 'operation',
      render: (text, record) => {
        return (
          page && page.content && page.content.length > 0 ?
            (
              <Popconfirm title="确认删除?" onConfirm={() => dispatch({ type: 'REAPFA0002/delete', id: record.id })}>
                <a href="#">删除</a>
              </Popconfirm>
            ) : null
        )
      },
    },
  ]

  return (
    <Col>
      <Row gutter={12} className={styles.searchForm}>
        <Col span={4}>
          <Item label="路由名称">
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0002/setState',
                  search: {
                    name: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col span={4}>
          <Item label="路由规则">
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0002/setState',
                  search: {
                    path: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col span={4}>
          <Item label="服务ID">
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0002/setState',
                  search: {
                    serviceId: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col span={4}>
          <Item label="转发地址">
            <Input
              onChange={(e) => {
                const { value } = e.target
                dispatch({
                  type: 'REAPFA0002/setState',
                  search: {
                    path: value,
                  },
                })
              }}
            />
          </Item>
        </Col>
        <Col span={4} >
          <Item>
            <Button
              type="primary"
              htmlType="button"
              icon="search"
              onClick={
                () => dispatch({ type: 'REAPFA0002/query' })
              }
            >
            查询
            </Button>
          </Item>
        </Col>
      </Row>
      <Col>
        <FunctionForm showCreateModal={showCreateModal} dispatch={dispatch} />
      </Col>
      <Col>
        <Table dataSource={page && page.content}
          pagination={{
            total: page && page.totalElements,
            showTotal: total => `总记录数 ${total} `,
            onChange: (number, size) => (dispatch({ type: 'REAPFA0002/query', page: number - 1, size })),
        }}
          rowKey="id"
          columns={columns}
          loading={loading}
          bordered
        />
      </Col>
    </Col>
  )
}
export default Component
